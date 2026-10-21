package web.pulso.service.escala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.SetorRepository;
import web.pulso.service.resolver.escala.EscalaContextoResolver;
import web.pulso.service.resolver.escala.EscalaContexto;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("EscalaContextoResolver")
class EscalaContextoResolverTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private SetorRepository setorRepository;

    @InjectMocks
    private EscalaContextoResolver escalaContextoResolver;

    private static final Long USUARIO_ID_VALIDO   = 1L;
    private static final Long USUARIO_ID_INVALIDO = 321L;
    private static final Long SETOR_ID_VALIDO     = 1L;
    private static final Long SETOR_ID_INVALIDO   = 321L;

    private Profissional liderValido;
    private Setor setorValido;

    @BeforeEach
    void setUp() {
        liderValido = spy(new Profissional());
        liderValido.setId(USUARIO_ID_VALIDO);

        setorValido = new Setor();
        setorValido.setId(SETOR_ID_VALIDO);
    }

    @Test
    @DisplayName("[CE-valido] Deve retornar EscalaContexto com lider e setor quando dados forem válidos e lider for do setor")
    void deveRetornarContextoQuandoDadosForemValidos() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_VALIDO))
                .thenReturn(Optional.of(setorValido));
        doReturn(true).when(liderValido).eLiderNoSetor(setorValido);

        // Act
        EscalaContexto contexto = escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO);

        // Assert
        assertThat(contexto).isNotNull();
        assertThat(contexto.lider()).isEqualTo(liderValido);
        assertThat(contexto.setor()).isEqualTo(setorValido);
    }

    @Test
    @DisplayName("[CE-valido] Deve retornar lider e setor exatos encontrados pelos repositórios")
    void deveRetornarAsInstanciasExatasDoRepositorio() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_VALIDO))
                .thenReturn(Optional.of(setorValido));
        doReturn(true).when(liderValido).eLiderNoSetor(setorValido);

        // Act
        EscalaContexto contexto = escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO);

        // Assert — mesmas instâncias retornadas pelos repositórios, sem transformação
        assertThat(contexto.lider()).isSameAs(liderValido);
        assertThat(contexto.setor()).isSameAs(setorValido);
    }

    @Test
    @DisplayName("[CE-usuario-invalido] Deve lançar NotFoundException quando usuarioId não existir")
    void deveLancarNotFoundQuandoUsuarioIdNaoExistir() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> escalaContextoResolver.resolver(USUARIO_ID_INVALIDO, SETOR_ID_VALIDO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Líder");

        verifyNoInteractions(setorRepository);
    }

    @Test
    @DisplayName("[CE-setor-invalido] Deve lançar NotFoundException quando setorId não existir")
    void deveLancarNotFoundQuandoSetorIdNaoExistir() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_INVALIDO))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Setor");
    }

    @Test
    @DisplayName("[CE-permissao-invalida] Deve lançar ForbiddenException quando lider não for líder do setor informado")
    void deveLancarForbiddenQuandoLiderNaoForDoSetor() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_VALIDO))
                .thenReturn(Optional.of(setorValido));
        doReturn(false).when(liderValido).eLiderNoSetor(setorValido);

        // Act & Assert
        assertThatThrownBy(() -> escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .isInstanceOf(ForbiddenException.class)
                .hasMessageContaining("líder");
    }

    @Test
    @DisplayName("[CE-permissao-invalida] Não deve retornar contexto quando lider não tiver permissão no setor")
    void naoDeveRetornarContextoQuandoSemPermissao() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_VALIDO))
                .thenReturn(Optional.of(setorValido));
        doReturn(false).when(liderValido).eLiderNoSetor(setorValido);

        // Act & Assert
        assertThatThrownBy(() -> escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO))
                .isInstanceOf(ForbiddenException.class);

        // Contexto nunca é construído — as chamadas param na exceção
        verify(liderValido).eLiderNoSetor(setorValido);
    }

    @Test
    @DisplayName("[CE-ordem] Deve buscar profissional antes do setor")
    void deveBuscarProfissionalAntesDoSetor() {
        // Arrange — profissional não existe, setor nem deve ser consultado
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act
        assertThatThrownBy(() -> escalaContextoResolver.resolver(USUARIO_ID_INVALIDO, SETOR_ID_VALIDO))
                .isInstanceOf(NotFoundException.class);

        // Assert — setor nunca buscado se profissional falhou
        verify(profissionalRepository).findByUsuarioId(USUARIO_ID_INVALIDO);
        verifyNoInteractions(setorRepository);
    }

    @Test
    @DisplayName("[CE-ordem] Deve verificar permissão somente após encontrar profissional e setor")
    void deveVerificarPermissaoSomenteAposEncontrarAmbos() {
        // Arrange — setor não existe, eLiderNoSetor não deve ser chamado
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_INVALIDO))
                .thenReturn(Optional.empty());

        // Act
        assertThatThrownBy(() -> escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_INVALIDO))
                .isInstanceOf(NotFoundException.class);

        // Assert — permissão nunca verificada se setor não existe
        verify(liderValido, never()).eLiderNoSetor(any());
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve buscar profissional com o usuarioId exato recebido")
    void deveBuscarProfissionalComUsuarioIdExato() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_VALIDO))
                .thenReturn(Optional.of(setorValido));
        doReturn(true).when(liderValido).eLiderNoSetor(setorValido);

        // Act
        escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO);

        // Assert
        verify(profissionalRepository).findByUsuarioId(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve buscar setor com o setorId exato recebido")
    void deveBuscarSetorComSetorIdExato() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_VALIDO))
                .thenReturn(Optional.of(setorValido));
        doReturn(true).when(liderValido).eLiderNoSetor(setorValido);

        // Act
        escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO);

        // Assert
        verify(setorRepository).findById(SETOR_ID_VALIDO);
    }

    @Test
    @DisplayName("[CE-colaboracao] Deve verificar permissão passando o setor exato encontrado")
    void deveVerificarPermissaoComSetorExato() {
        // Arrange
        when(profissionalRepository.findByUsuarioId(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(liderValido));
        when(setorRepository.findById(SETOR_ID_VALIDO))
                .thenReturn(Optional.of(setorValido));
        doReturn(true).when(liderValido).eLiderNoSetor(setorValido);

        // Act
        escalaContextoResolver.resolver(USUARIO_ID_VALIDO, SETOR_ID_VALIDO);

        // Assert — eLiderNoSetor chamado com a instância exata do setor retornada pelo repositório
        verify(liderValido).eLiderNoSetor(setorValido);
    }
}