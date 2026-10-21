package web.pulso.service.notificacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import web.pulso.entity.Notificacao;
import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.exception.business.NotFoundException;
import web.pulso.repository.NotificacaoRepository;
import web.pulso.service.NotificacaoService;
import web.pulso.service.mappers.NotificacaoMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificacaoService - foiLido")
class AtualizarNotificacaoServiceTest {

    @Mock
    private NotificacaoRepository notificacaoRepo;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private NotificacaoMapper mapper;

    @InjectMocks
    private NotificacaoService notificacaoService;

    private static final Long NOTIFICACAO_ID_VALIDO   = 1L;
    private static final Long NOTIFICACAO_ID_INVALIDO = 321L;

    private Notificacao notificacaoPendente;

    @BeforeEach
    void setUp() {
        notificacaoPendente = new Notificacao();
        notificacaoPendente.setStatus(StatusNotificacao.PENDENTE);
    }

    @Test
    @DisplayName("[CE-valido] Deve executar foiLido sem lançar exceção quando notificacao existir")
    void deveExecutarFoiLidoSemExcecao() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act & Assert
        assertThatCode(() -> notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("[CE-status] Deve definir status LIDA na entidade antes de salvar")
    void deveDefinirStatusLidaAntesDeSalvar() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class))).thenAnswer(inv -> {
            Notificacao salva = inv.getArgument(0);
            assertThat(salva.getStatus()).isEqualTo(StatusNotificacao.LIDA);
            return salva;
        });

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        verify(notificacaoRepo).save(any(Notificacao.class));
    }

    @Test
    @DisplayName("[CE-status] Status deve ser LIDA após foiLido independente do status anterior")
    void statusDeveSerLidaAposFoiLido() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        // Assert
        assertThat(notificacaoPendente.getStatus()).isEqualTo(StatusNotificacao.LIDA);
    }

    @Test
    @DisplayName("[CE-dataLeitura] Deve definir dataLeitura como não nula após foiLido")
    void deveDefinirDataLeituraNaoNula() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        // Assert
        assertThat(notificacaoPendente.getDataLeitura()).isNotNull();
    }

    @Test
    @DisplayName("[CE-dataLeitura] dataLeitura deve ser próxima ao momento da execução do teste")
    void dataLeituraDeveSerProximaAoMomento() {
        // Arrange
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);

        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        LocalDateTime depois = LocalDateTime.now().plusSeconds(1);

        // Assert — dataLeitura deve estar dentro da janela de execução do teste
        assertThat(notificacaoPendente.getDataLeitura())
                .isAfterOrEqualTo(antes)
                .isBeforeOrEqualTo(depois);
    }

    @Test
    @DisplayName("[CE-dataLeitura] Deve definir status LIDA e dataLeitura atomicamente antes do save")
    void deveDefinirStatusEDataLeituraAntesDeSalvar() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class))).thenAnswer(inv -> {
            Notificacao salva = inv.getArgument(0);
            // Ambos devem estar definidos quando save é chamado
            assertThat(salva.getStatus()).isEqualTo(StatusNotificacao.LIDA);
            assertThat(salva.getDataLeitura()).isNotNull();
            return salva;
        });

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        verify(notificacaoRepo).save(any(Notificacao.class));
    }

    @Test
    @DisplayName("[CE-id-invalido] Deve lançar NotFoundException quando notificacaoId não existir")
    void deveLancarNotFoundQuandoIdNaoExistir() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> notificacaoService.foiLido(NOTIFICACAO_ID_INVALIDO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Notificação");

        verify(notificacaoRepo, never()).save(any());
    }

    @Test
    @DisplayName("[CE-id-invalido] Deve lançar NotFoundException antes de qualquer mutação de estado")
    void deveLancarNotFoundAntesDeQualquerMutacao() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act
        assertThatThrownBy(() -> notificacaoService.foiLido(NOTIFICACAO_ID_INVALIDO))
                .isInstanceOf(NotFoundException.class);

        // Assert — findById chamado, mais nada
        verify(notificacaoRepo).findById(NOTIFICACAO_ID_INVALIDO);
        verifyNoMoreInteractions(notificacaoRepo);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("[CE-ordem] Deve buscar notificacao antes de salvar")
    void deveBuscarAntesDoSave() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        // Assert
        var inOrder = inOrder(notificacaoRepo);
        inOrder.verify(notificacaoRepo).findById(NOTIFICACAO_ID_VALIDO);
        inOrder.verify(notificacaoRepo).save(any(Notificacao.class));
    }

    @Test
    @DisplayName("[CE-colaboracao] Não deve interagir com mapper em nenhum cenário de foiLido")
    void naoDeveInteragirComMapper() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        // Assert — foiLido é void e não serializa resposta
        verifyNoInteractions(mapper);
        verifyNoInteractions(messagingTemplate);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve salvar a mesma instância de notificacao encontrada pelo findById")
    void deveSalvarAMesmaInstancia() {
        // Arrange
        when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                .thenReturn(Optional.of(notificacaoPendente));
        when(notificacaoRepo.save(any(Notificacao.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        notificacaoService.foiLido(NOTIFICACAO_ID_VALIDO);

        // Assert — save recebe a instância mutada, não uma nova
        verify(notificacaoRepo).save(notificacaoPendente);
    }
}
