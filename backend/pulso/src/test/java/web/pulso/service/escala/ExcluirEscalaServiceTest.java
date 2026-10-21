package web.pulso.service.escala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.pulso.entity.Escala;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.exception.business.NotFoundException;
import web.pulso.exception.business.UnprocessableEntityException;
import web.pulso.repository.EscalaRepository;
import web.pulso.service.EscalaService;
import web.pulso.service.PlantaoService;
import web.pulso.service.mappers.EscalaMapper;
import web.pulso.service.resolver.escala.EscalaContextoResolver;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EscalaService - Excluir")
class ExcluirEscalaServiceTest {

    @Mock
    private EscalaMapper escalaMapper;

    @Mock
    private EscalaRepository escalaRepository;

    @Mock
    private EscalaContextoResolver contextoResolver;

    @Mock
    private PlantaoService plantaoService;

    @InjectMocks
    private EscalaService escalaService;

    private static final Long ESCALA_ID_VALIDO   = 1L;
    private static final Long ESCALA_ID_INVALIDO = 321L;

    private Setor setorValido;
    private LocalDate mesValido;

    @BeforeEach
    void setUp() {
        setorValido = new Setor();
        setorValido.setId(1L);

        mesValido = LocalDate.now().plusMonths(2).withDayOfMonth(1);
    }

    @Test
    @DisplayName("[CE-valido] Deve excluir escala com sucesso quando status for RASCUNHO")
    void deveExcluirComSucessoQuandoStatusForRascunho() {
        // Arrange
        Escala escalaRascunho = buildEscala(StatusEscala.RASCUNHO);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        doNothing().when(escalaRepository).delete(escalaRascunho);

        // Act
        assertThatCode(() -> escalaService.excluir(ESCALA_ID_VALIDO))
                .doesNotThrowAnyException();

        // Assert
        verify(escalaRepository).delete(escalaRascunho);
    }

    @Test
    @DisplayName("[CE-valido] Deve chamar delete com a escala correta encontrada pelo id")
    void deveChamarDeleteComAEscalaCorreta() {
        // Arrange
        Escala escalaRascunho = buildEscala(StatusEscala.RASCUNHO);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        doNothing().when(escalaRepository).delete(any(Escala.class));

        // Act
        escalaService.excluir(ESCALA_ID_VALIDO);

        // Assert — delete deve receber a mesma instância retornada pelo findById
        verify(escalaRepository).delete(escalaRascunho);
    }

    @Test
    @DisplayName("[CE-id-invalido] Deve lançar NotFoundException quando escalaId não existir")
    void deveLancarNotFoundQuandoEscalaIdNaoExistir() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> escalaService.excluir(ESCALA_ID_INVALIDO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("escala");

        verify(escalaRepository, never()).delete(any(Escala.class));
    }

    @Test
    @DisplayName("[CE-id-invalido] Deve lançar NotFoundException antes de checar podeSerExcluida")
    void deveLancarNotFoundAntesDeChecarEstado() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act
        assertThatThrownBy(() -> escalaService.excluir(ESCALA_ID_INVALIDO))
                .isInstanceOf(NotFoundException.class);

        // Assert — findById chamado, nada mais
        verify(escalaRepository).findById(ESCALA_ID_INVALIDO);
        verifyNoMoreInteractions(escalaRepository);
    }


    @Test
    @DisplayName("[CE-status-invalido] Deve lançar UnprocessableEntityException quando escala estiver PUBLICADA")
    void deveLancarUnprocessableQuandoEscalaEstiverPublicada() {
        // Arrange
        Escala escalaPublicada = buildEscala(StatusEscala.PUBLICADA);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaPublicada));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.excluir(ESCALA_ID_VALIDO))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessageContaining("publicada");

        verify(escalaRepository, never()).delete(any(Escala.class));
    }

    @Test
    @DisplayName("[CE-status-invalido] Deve lançar UnprocessableEntityException quando escala estiver ARQUIVADA")
    void deveLancarUnprocessableQuandoEscalaEstiverArquivada() {
        // Arrange
        // ARQUIVADA também falha em podeSerExcluida() pois só RASCUNHO retorna true.
        Escala escalaArquivada = buildEscala(StatusEscala.ARQUIVADA);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaArquivada));

        // Act & Assert
        assertThatThrownBy(() -> escalaService.excluir(ESCALA_ID_VALIDO))
                .isInstanceOf(UnprocessableEntityException.class);

        verify(escalaRepository, never()).delete(any(Escala.class));
    }

    @Test
    @DisplayName("[CE-ordem] Deve buscar escala antes de verificar se pode ser excluída")
    void deveBuscarEscalaAntesDeVerificarEstado() {
        // Arrange
        Escala escalaRascunho = buildEscala(StatusEscala.RASCUNHO);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        doNothing().when(escalaRepository).delete(any(Escala.class));

        // Act
        escalaService.excluir(ESCALA_ID_VALIDO);

        // Assert
        var inOrder = inOrder(escalaRepository);
        inOrder.verify(escalaRepository).findById(ESCALA_ID_VALIDO);
        inOrder.verify(escalaRepository).delete(escalaRascunho);
    }

    @Test
    @DisplayName("[CE-ordem] Não deve chamar delete quando escala não puder ser excluída")
    void naoDeveChamarDeleteQuandoEscalaNaoPodeSerExcluida() {
        // Arrange
        Escala escalaPublicada = buildEscala(StatusEscala.PUBLICADA);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaPublicada));

        // Act
        assertThatThrownBy(() -> escalaService.excluir(ESCALA_ID_VALIDO))
                .isInstanceOf(UnprocessableEntityException.class);

        // Assert
        verify(escalaRepository).findById(ESCALA_ID_VALIDO);
        verify(escalaRepository, never()).delete(any(Escala.class));
        verifyNoMoreInteractions(escalaRepository);
    }


    @Test
    @DisplayName("[CE-colaboracao] Não deve interagir com mapper nem com outras dependências ao excluir")
    void naoDeveInteragirComMapperNemOutrasDependencias() {
        // Arrange
        Escala escalaRascunho = buildEscala(StatusEscala.RASCUNHO);

        when(escalaRepository.findById(ESCALA_ID_VALIDO))
                .thenReturn(Optional.of(escalaRascunho));
        doNothing().when(escalaRepository).delete(any(Escala.class));

        // Act
        escalaService.excluir(ESCALA_ID_VALIDO);

        // Assert — excluir é void, não passa pelo mapper nem pelo contextoResolver
        verifyNoInteractions(escalaMapper);
        verifyNoInteractions(contextoResolver);
        verifyNoInteractions(plantaoService);
    }

    @Test
    @DisplayName("[CE-colaboracao] Não deve interagir com nenhuma dependência além do repositório quando escala não existir")
    void naoDeveInteragirComNadaAlmDoRepositorioQuandoNaoEncontrar() {
        // Arrange
        when(escalaRepository.findById(ESCALA_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act
        assertThatThrownBy(() -> escalaService.excluir(ESCALA_ID_INVALIDO))
                .isInstanceOf(NotFoundException.class);

        // Assert
        verifyNoInteractions(escalaMapper);
        verifyNoInteractions(contextoResolver);
        verifyNoInteractions(plantaoService);
    }


    private Escala buildEscala(StatusEscala status) {
        Escala escala = new Escala();
        escala.setNome("Escala Teste");
        escala.setMesReferencia(mesValido);
        escala.setSetor(setorValido);
        escala.setStatus(status);
        return escala;
    }
}