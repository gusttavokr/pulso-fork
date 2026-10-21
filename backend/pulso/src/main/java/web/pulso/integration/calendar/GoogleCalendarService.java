package web.pulso.integration.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.pulso.integration.calendar.dto.GoogleCalendarEventDTO;
import web.pulso.exception.InfrastructureException;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.entity.GoogleCalendarToken;
import web.pulso.entity.Plantao;
import web.pulso.entity.Usuario;
import web.pulso.repository.GoogleCalendarTokenRepository;
import web.pulso.repository.UsuarioRepository;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "Pulso - Gestão de Plantões";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    @Value("${google.oauth.client-id}")
    private String clientId;

    @Value("${google.oauth.client-secret}")
    private String clientSecret;

    @Value("${google.oauth.redirect-uri}")
    private String redirectUri;

    private final GoogleCalendarTokenRepository tokenRepository;
    private final UsuarioRepository usuarioRepository;

    public GoogleCalendarService(
            GoogleCalendarTokenRepository tokenRepository,
            UsuarioRepository usuarioRepository) {
        this.tokenRepository = tokenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public String getAuthorizationUrl(String email) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

   
        // Validar configuração OAuth antes de prosseguir
        if (clientId == null || clientId.equals("your-client-id-here") || clientId.trim().isEmpty()) {
            throw new InfrastructureException(
                "As credenciais OAuth do Google não estão configuradas no servidor. " +
                "Por favor, consulte a documentação em doc/google-calendar-integration.md " +
                "para configurar as credenciais no Google Cloud Console.");
        }

        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .setState(usuario.getId().toString())
                .build();
    }

    @Transactional
    public void saveTokenFromCode(String code, String email) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        GoogleAuthorizationCodeFlow flow = getFlow();
        GoogleTokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(redirectUri)
                .execute();

        GoogleCalendarToken token = tokenRepository.findByUsuario(usuario)
                .orElse(new GoogleCalendarToken());

        token.setUsuario(usuario);
        token.setAccessToken(tokenResponse.getAccessToken());
        token.setRefreshToken(tokenResponse.getRefreshToken());
        
        // Expira em 1 hora (Google padrão)
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(tokenResponse.getExpiresInSeconds());
        token.setExpiresAt(expiresAt);

        tokenRepository.save(token);
    }

    @Transactional
    public void refreshAccessToken(GoogleCalendarToken token) throws Exception {
        GoogleAuthorizationCodeFlow flow = getFlow();
        
        com.google.api.client.auth.oauth2.TokenResponse tokenResponse = 
            flow.newTokenRequest(token.getRefreshToken())
                .setGrantType("refresh_token")
                .execute();

        token.setAccessToken(tokenResponse.getAccessToken());
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(tokenResponse.getExpiresInSeconds());
        token.setExpiresAt(expiresAt);

        tokenRepository.save(token);
    }

    public String addPlantaoToCalendar(Plantao plantao, String email) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        GoogleCalendarToken token = tokenRepository.findByUsuario(usuario)
                .orElseThrow(() -> new ForbiddenException(
                        "Usuário não autorizou acesso ao Google Calendar. Por favor, conecte sua conta."));

        if (token.isExpired()) {
            refreshAccessToken(token);
        }

        Calendar service = getCalendarService(token.getAccessToken());

        Event event = new Event()
                .setSummary("Plantão - " + plantao.getEscala().getSetor().getNome())
                .setDescription(buildDescription(plantao))
                .setLocation(plantao.getEscala().getSetor().getInstituicao().getNome());

        EventDateTime start = new EventDateTime()
                .setDateTime(toDateTime(plantao.getHoraEntrada()))
                .setTimeZone("America/Sao_Paulo");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(toDateTime(plantao.getHoraSaida()))
                .setTimeZone("America/Sao_Paulo");
        event.setEnd(end);

        Event createdEvent = service.events().insert("primary", event).execute();
        return createdEvent.getId();
    }

    public List<Event> getUpcomingEvents(String email, int maxResults) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        GoogleCalendarToken token = tokenRepository.findByUsuario(usuario)
                .orElseThrow(() -> new ForbiddenException(
                        "Usuário não autorizou acesso ao Google Calendar."));

        if (token.isExpired()) {
            refreshAccessToken(token);
        }

        Calendar service = getCalendarService(token.getAccessToken());

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(maxResults)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events.getItems();
    }
    
    public List<GoogleCalendarEventDTO> getUpcomingEventsDTO(String email, int maxResults) throws Exception {
        List<Event> events = getUpcomingEvents(email, maxResults);
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private GoogleCalendarEventDTO convertToDTO(Event event) {
        GoogleCalendarEventDTO dto = new GoogleCalendarEventDTO();
        dto.setId(event.getId());
        dto.setSummary(event.getSummary());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        
        // Converte start
        GoogleCalendarEventDTO.EventDateTime startDTO = new GoogleCalendarEventDTO.EventDateTime();
        if (event.getStart() != null) {
            if (event.getStart().getDateTime() != null) {
                startDTO.setDateTime(event.getStart().getDateTime().toString());
            }
            if (event.getStart().getDate() != null) {
                startDTO.setDate(event.getStart().getDate().toString());
            }
            startDTO.setTimeZone(event.getStart().getTimeZone());
        }
        dto.setStart(startDTO);
        
        // Converte end
        GoogleCalendarEventDTO.EventDateTime endDTO = new GoogleCalendarEventDTO.EventDateTime();
        if (event.getEnd() != null) {
            if (event.getEnd().getDateTime() != null) {
                endDTO.setDateTime(event.getEnd().getDateTime().toString());
            }
            if (event.getEnd().getDate() != null) {
                endDTO.setDate(event.getEnd().getDate().toString());
            }
            endDTO.setTimeZone(event.getEnd().getTimeZone());
        }
        dto.setEnd(endDTO);
        
        return dto;
    }

    @Transactional
    public void disconnectCalendar(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        tokenRepository.findByUsuario(usuario).ifPresent(tokenRepository::delete);
    }

    public boolean isConnected(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) {
            return false;
        }
        return tokenRepository.findByUsuarioId(usuario.getId()).isPresent();
    }

    private GoogleAuthorizationCodeFlow getFlow() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        String clientSecretsJson = String.format(
                "{\"installed\":{\"client_id\":\"%s\",\"client_secret\":\"%s\"}}",
                clientId, clientSecret);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new StringReader(clientSecretsJson));

        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
    }

    private Calendar getCalendarService(String accessToken) throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Credential credential = new Credential.Builder(
                com.google.api.client.auth.oauth2.BearerToken.authorizationHeaderAccessMethod())
                .setJsonFactory(JSON_FACTORY)
                .setTransport(httpTransport)
                .build()
                .setAccessToken(accessToken);

        return new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private DateTime toDateTime(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        return new DateTime(date);
    }

    private String buildDescription(Plantao plantao) {
        StringBuilder desc = new StringBuilder();
        desc.append("Plantão médico\n\n");
        desc.append("Setor: ").append(plantao.getEscala().getSetor().getNome()).append("\n");
        desc.append("Instituição: ").append(plantao.getEscala().getSetor().getInstituicao().getNome()).append("\n");
        
        if (plantao.getEstimativaValor() > 0) {
            desc.append("Valor estimado: R$ ").append(plantao.getEstimativaValor()).append("\n");
        }
        
        if (plantao.getComentario() != null && !plantao.getComentario().isEmpty()) {
            desc.append("\nComentários: ").append(plantao.getComentario());
        }
        
        return desc.toString();
    }
}
