package web.pulso.service.notificacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import web.pulso.dto.response.notificacao.NotificacaoDTO;
import web.pulso.entity.Notificacao;
import web.pulso.exception.business.NotFoundException;
import web.pulso.repository.NotificacaoRepository;
import web.pulso.service.NotificacaoService;
import web.pulso.service.mappers.NotificacaoMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("NotificacaoService - Buscar")
class BuscarNotificacaoServiceTest {

    @Mock
    private NotificacaoRepository notificacaoRepo;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private NotificacaoMapper mapper;

    @InjectMocks
    private NotificacaoService notificacaoService;

    private static final Long USUARIO_ID_VALIDO       = 1L;
    private static final Long NOTIFICACAO_ID_VALIDO   = 1L;
    private static final Long NOTIFICACAO_ID_INVALIDO = 321L;

    private NotificacaoDTO dtoResposta;

    @BeforeEach
    void setUp() {
        dtoResposta = mock(NotificacaoDTO.class);
    }


    @Nested
    @DisplayName("buscarNotificacaoDeUsuarioId")
    class BuscarPorUsuario {

        @Test
        @DisplayName("[CE-valido] Deve retornar lista com DTOs mapeados quando usuario tiver notificacoes")
        void deveRetornarListaMapeadaQuandoHouverNotificacoes() {
            // Arrange
            List<Notificacao> entidades = List.of(new Notificacao(), new Notificacao(), new Notificacao());

            when(notificacaoRepo.findByDestinatarioId(USUARIO_ID_VALIDO))
                    .thenReturn(entidades);
            when(mapper.toResponseDTO(any(Notificacao.class)))
                    .thenReturn(dtoResposta);

            // Act
            List<NotificacaoDTO> resultado = notificacaoService.buscarNotificacaoDeUsuarioId(USUARIO_ID_VALIDO);

            // Assert
            assertThat(resultado).hasSize(3);
            verify(mapper, times(3)).toResponseDTO(any(Notificacao.class));
        }

        @Test
        @DisplayName("[CE-valido] Deve retornar lista vazia quando usuario não tiver notificacoes")
        void deveRetornarListaVaziaQuandoNaoHouverNotificacoes() {
            // Arrange
            when(notificacaoRepo.findByDestinatarioId(USUARIO_ID_VALIDO))
                    .thenReturn(Collections.emptyList());

            // Act
            List<NotificacaoDTO> resultado = notificacaoService.buscarNotificacaoDeUsuarioId(USUARIO_ID_VALIDO);

            // Assert
            assertThat(resultado).isNotNull().isEmpty();
            verifyNoInteractions(mapper);
        }

        @Test
        @DisplayName("[CE-valido] Deve buscar notificacoes com o usuarioId exato recebido")
        void deveBuscarComUsuarioIdExato() {
            // Arrange
            when(notificacaoRepo.findByDestinatarioId(USUARIO_ID_VALIDO))
                    .thenReturn(Collections.emptyList());

            // Act
            notificacaoService.buscarNotificacaoDeUsuarioId(USUARIO_ID_VALIDO);

            // Assert
            verify(notificacaoRepo).findByDestinatarioId(USUARIO_ID_VALIDO);
        }

        @Test
        @DisplayName("[CE-valido] Deve mapear cada notificacao individualmente via mapper")
        void deveMapearCadaNotificacaoIndividualmente() {
            // Arrange
            Notificacao n1 = new Notificacao();
            Notificacao n2 = new Notificacao();

            when(notificacaoRepo.findByDestinatarioId(USUARIO_ID_VALIDO))
                    .thenReturn(List.of(n1, n2));
            when(mapper.toResponseDTO(any(Notificacao.class)))
                    .thenReturn(dtoResposta);

            // Act
            notificacaoService.buscarNotificacaoDeUsuarioId(USUARIO_ID_VALIDO);

            // Assert — cada instância passada individualmente ao mapper
            verify(mapper).toResponseDTO(n1);
            verify(mapper).toResponseDTO(n2);
        }

        @Test
        @DisplayName("[CE-colaboracao] Não deve interagir com messagingTemplate ao buscar por usuario")
        void naoDeveInteragirComWebsocket() {
            // Arrange
            when(notificacaoRepo.findByDestinatarioId(USUARIO_ID_VALIDO))
                    .thenReturn(Collections.emptyList());

            // Act
            notificacaoService.buscarNotificacaoDeUsuarioId(USUARIO_ID_VALIDO);

            // Assert
            verifyNoInteractions(messagingTemplate);
        }
    }

    @Nested
    @DisplayName("buscarPorId")
    class BuscarPorId {

        @Test
        @DisplayName("[CE-valido] Deve retornar DTO mapeado quando notificacao existir")
        void deveRetornarDtoMapeadoQuandoExistir() {
            // Arrange
            Notificacao entidade = new Notificacao();

            when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                    .thenReturn(Optional.of(entidade));
            when(mapper.toResponseDTO(entidade))
                    .thenReturn(dtoResposta);

            // Act
            NotificacaoDTO resultado = notificacaoService.buscarPorId(NOTIFICACAO_ID_VALIDO);

            // Assert
            assertThat(resultado).isSameAs(dtoResposta);
        }

        @Test
        @DisplayName("[CE-valido] Deve chamar mapper com a entidade exata retornada pelo repositório")
        void deveChamarMapperComEntidadeExata() {
            // Arrange
            Notificacao entidade = new Notificacao();

            when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                    .thenReturn(Optional.of(entidade));
            when(mapper.toResponseDTO(entidade))
                    .thenReturn(dtoResposta);

            // Act
            notificacaoService.buscarPorId(NOTIFICACAO_ID_VALIDO);

            // Assert
            verify(mapper).toResponseDTO(entidade);
        }

        @Test
        @DisplayName("[CE-id-invalido] Deve lançar NotFoundException quando notificacaoId não existir")
        void deveLancarNotFoundQuandoIdNaoExistir() {
            // Arrange
            when(notificacaoRepo.findById(NOTIFICACAO_ID_INVALIDO))
                    .thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> notificacaoService.buscarPorId(NOTIFICACAO_ID_INVALIDO))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Notificação");

            verifyNoInteractions(mapper);
        }

        @Test
        @DisplayName("[CE-id-invalido] Não deve chamar mapper quando notificacao não for encontrada")
        void naoDeveChamarMapperQuandoNaoEncontrar() {
            // Arrange
            when(notificacaoRepo.findById(NOTIFICACAO_ID_INVALIDO))
                    .thenReturn(Optional.empty());

            // Act
            assertThatThrownBy(() -> notificacaoService.buscarPorId(NOTIFICACAO_ID_INVALIDO))
                    .isInstanceOf(NotFoundException.class);

            // Assert
            verifyNoInteractions(mapper);
            verifyNoInteractions(messagingTemplate);
        }

        @Test
        @DisplayName("[CE-colaboracao] Não deve interagir com messagingTemplate ao buscar por id")
        void naoDeveInteragirComWebsocket() {
            // Arrange
            Notificacao entidade = new Notificacao();

            when(notificacaoRepo.findById(NOTIFICACAO_ID_VALIDO))
                    .thenReturn(Optional.of(entidade));
            when(mapper.toResponseDTO(entidade))
                    .thenReturn(dtoResposta);

            // Act
            notificacaoService.buscarPorId(NOTIFICACAO_ID_VALIDO);

            // Assert
            verifyNoInteractions(messagingTemplate);
        }
    }
}
