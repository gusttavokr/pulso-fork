package web.pulso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import web.pulso.integration.calendar.GoogleCalendarService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/google-calendar")
@Tag(name = "Google Calendar", description = "Endpoints para integração com Google Calendar")
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;

    public GoogleCalendarController(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    @GetMapping("/auth-url")
    @Operation(summary = "Obter URL de autorização do Google", 
               description = "Retorna a URL para o usuário autorizar acesso ao Google Calendar")
    public ResponseEntity<Map<String, String>> getAuthUrl(@AuthenticationPrincipal Jwt jwt) {
        try {
            String email = jwt.getSubject();
            String authUrl = googleCalendarService.getAuthorizationUrl(email);
            
            Map<String, String> response = new HashMap<>();
            response.put("authUrl", authUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/callback")
    @Operation(summary = "Callback OAuth2", 
               description = "Processa o código de autorização retornado pelo Google")
    public ResponseEntity<Map<String, String>> handleCallback(
            @RequestParam("code") String code,
            @AuthenticationPrincipal Jwt jwt) {
        try {
            String email = jwt.getSubject();
            googleCalendarService.saveTokenFromCode(code, email);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Conta Google Calendar conectada com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Erro ao conectar com Google Calendar: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/events")
    @Operation(summary = "Listar próximos eventos", 
               description = "Retorna os próximos eventos do calendário do usuário")
    public ResponseEntity<?> getUpcomingEvents(
            @RequestParam(defaultValue = "10") int maxResults,
            @AuthenticationPrincipal Jwt jwt) {
        try {
            String email = jwt.getSubject();
            var events = googleCalendarService.getUpcomingEventsDTO(email, maxResults);
            
           
            
            return ResponseEntity.ok(events);
        } catch (IllegalStateException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            response.put("connected", "false");
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/status")
    @Operation(summary = "Verificar status da conexão", 
               description = "Verifica se o usuário tem o Google Calendar conectado")
    public ResponseEntity<Map<String, Boolean>> getConnectionStatus(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        boolean connected = googleCalendarService.isConnected(email);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("connected", connected);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/disconnect")
    @Operation(summary = "Desconectar Google Calendar", 
               description = "Remove a autorização do Google Calendar")
    public ResponseEntity<Map<String, String>> disconnect(@AuthenticationPrincipal Jwt jwt) {
        try {
            String email = jwt.getSubject();
            googleCalendarService.disconnectCalendar(email);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Google Calendar desconectado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Erro ao desconectar: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
