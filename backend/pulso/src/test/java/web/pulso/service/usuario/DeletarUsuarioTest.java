package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método deletar da classe UsuarioService")
class DeletarUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuarioComSucesso() {

        Long id = 1L;

        when(usuarioRepository.existsById(id))
                .thenReturn(true);

        doNothing()
                .when(usuarioRepository)
                .deleteById(id);

        assertDoesNotThrow(
                () -> usuarioService.deletar(id));

        verify(usuarioRepository).existsById(id);

        verify(usuarioRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não existir")
    void deveLancarErroQuandoUsuarioNaoExistir() {

        Long id = 999L;

        when(usuarioRepository.existsById(id))
                .thenReturn(false);

        assertThrows(
                NotFoundException.class,
                () -> usuarioService.deletar(id));

        verify(usuarioRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(usuarioRepository.existsById(null))
                .thenThrow(new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.deletar(null));

        verify(usuarioRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando deleteById lançar exceção")
    void deveLancarErroQuandoDeleteByIdLancarExcecao() {

        Long id = 1L;

        when(usuarioRepository.existsById(id))
                .thenReturn(true);

        doThrow(new RuntimeException("Erro ao deletar"))
                .when(usuarioRepository)
                .deleteById(id);

        assertThrows(
                RuntimeException.class,
                () -> usuarioService.deletar(id));
    }
}
