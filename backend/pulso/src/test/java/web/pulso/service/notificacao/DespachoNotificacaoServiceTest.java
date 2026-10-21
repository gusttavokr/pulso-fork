package web.pulso.service.notificacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import web.pulso.dto.response.notificacao.NotificacaoDTO;
import web.pulso.entity.Notificacao;
import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.repository.NotificacaoRepository;
import web.pulso.service.NotificacaoService;
import web.pulso.service.mappers.NotificacaoMapper;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("NotificacaoService - despacho")
class DespachoNotificacaoServiceTest {

    @Mock
    private NotificacaoRepository notificacaoRepo;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private NotificacaoMapper mapper;

    @InjectMocks
    private NotificacaoService notificacaoService;

    private NotificacaoDTO dtoEntrada;
    private Notificacao entidadeMapeada;
    private Notificacao entidadeSalva;
    private NotificacaoDTO dtoResposta;

    @BeforeEach
    void setUp() {
        dtoEntrada = mock(NotificacaoDTO.class);

        entidadeMapeada = new Notificacao();
        // sem status — será definido pelo service antes do save

        entidadeSalva = new Notificacao();
        entidadeSalva.setStatus(StatusNotificacao.ENVIADA);

        dtoResposta = mock(NotificacaoDTO.class);
        when(dtoResposta.idDestinatario()).thenReturn(2L);
    }


    @Test
    @DisplayName("[CE-valido] Deve executar despacho sem lançar exceção quando dados forem válidos")
    void deveExecutarDespachoSemExcecao() {
        // Arrange
        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenReturn(entidadeSalva);
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act & Assert
        assertThatCode(() -> notificacaoService.despacho(dtoEntrada))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[CE-status] Deve definir status ENVIADA na entidade antes de salvar")
    void deveDefinirStatusEnviadaAntesDeSalvar() {
        // Arrange
        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenAnswer(inv -> {
            Notificacao salva = inv.getArgument(0);
            // Verifica que status já foi definido quando save é chamado
            assertThat(salva.getStatus()).isEqualTo(StatusNotificacao.ENVIADA);
            return entidadeSalva;
        });
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act
        notificacaoService.despacho(dtoEntrada);

        // Assert — verificação feita dentro do thenAnswer
        verify(notificacaoRepo).save(any(Notificacao.class));
    }

    @Test
    @DisplayName("[CE-status] Não deve salvar notificacao com status diferente de ENVIADA")
    void naoDeveSalvarComStatusDiferenteDeEnviada() {
        // Arrange — entidade começa sem status
        assertThat(entidadeMapeada.getStatus()).isNull();

        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenAnswer(inv -> {
            Notificacao salva = inv.getArgument(0);
            assertThat(salva.getStatus())
                    .isNotNull()
                    .isEqualTo(StatusNotificacao.ENVIADA);
            return entidadeSalva;
        });
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act
        notificacaoService.despacho(dtoEntrada);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve chamar mapper.toEntity com o DTO recebido")
    void deveChamarToEntityComDtoRecebido() {
        // Arrange
        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenReturn(entidadeSalva);
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act
        notificacaoService.despacho(dtoEntrada);

        // Assert
        verify(mapper).toEntity(dtoEntrada);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve salvar entidade após definir status")
    void deveSalvarEntidadeAposDefinirStatus() {
        // Arrange
        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenReturn(entidadeSalva);
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act
        notificacaoService.despacho(dtoEntrada);

        // Assert
        verify(notificacaoRepo).save(entidadeMapeada);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve chamar mapper.toResponseDTO com entidade retornada pelo save")
    void deveChamarToResponseDTOComEntidadeSalva() {
        // Arrange
        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenReturn(entidadeSalva);
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act
        notificacaoService.despacho(dtoEntrada);

        // Assert — toResponseDTO recebe a entidade pós-save, não a pré-save
        verify(mapper).toResponseDTO(entidadeSalva);
    }

    @Test
    @DisplayName("[CE-ordem] Deve chamar toEntity → save → toResponseDTO nessa ordem")
    void deveChamarDependenciasNaOrdemCorreta() {
        // Arrange
        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenReturn(entidadeSalva);
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act
        notificacaoService.despacho(dtoEntrada);

        // Assert
        var inOrder = inOrder(mapper, notificacaoRepo);
        inOrder.verify(mapper).toEntity(dtoEntrada);
        inOrder.verify(notificacaoRepo).save(any(Notificacao.class));
        inOrder.verify(mapper).toResponseDTO(entidadeSalva);
    }

    @Test
    @DisplayName("[CE-websocket] Deve chamar messagingTemplate após persistir e mapear resposta")
    void deveChamarWebsocketAposPersistir() {
        // Arrange
        when(mapper.toEntity(dtoEntrada)).thenReturn(entidadeMapeada);
        when(notificacaoRepo.save(any(Notificacao.class))).thenReturn(entidadeSalva);
        when(mapper.toResponseDTO(entidadeSalva)).thenReturn(dtoResposta);

        // Act
        notificacaoService.despacho(dtoEntrada);

        // Assert — verifica apenas que o template foi chamado depois do save,
        // sem validar payload (responsabilidade de teste de integração)
        var inOrder = inOrder(notificacaoRepo, messagingTemplate);
        inOrder.verify(notificacaoRepo).save(any(Notificacao.class));
        inOrder.verify(messagingTemplate).convertAndSendToUser(
                anyString(), anyString(), any());
    }
}
