package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static web.pulso.service.usuario.UsuarioFactory.criarUsuarioInativo;
import static web.pulso.service.usuario.UsuarioFactory.criarUsuarioValido;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método loadUserByUsername da classe UsuarioService")
class LoadUserByUsernameUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve carregar usuário por email com sucesso")
    void deveCarregarUsuarioPorEmailComSucesso() {

        String email = "usuario@pulso.com";

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(criarUsuarioValido()));

        UserDetails resultado = usuarioService.loadUserByUsername(email);

        assertEquals(email, resultado.getUsername());

        assertTrue(resultado.isEnabled());
    }

    @Test
    @DisplayName("Deve carregar usuário inativo como desabilitado")
    void deveCarregarUsuarioInativoComoDesabilitado() {

        String email = "usuario@pulso.com";

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(criarUsuarioInativo()));

        UserDetails resultado = usuarioService.loadUserByUsername(email);

        assertFalse(resultado.isEnabled());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não existir")
    void deveLancarErroQuandoUsuarioNaoExistir() {

        String email = "inexistente@pulso.com";

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> usuarioService.loadUserByUsername(email));
    }

    @Test
    @DisplayName("Deve lançar erro quando email for nulo")
    void deveLancarErroQuandoEmailForNulo() {

        when(usuarioRepository.findByEmail(null))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> usuarioService.loadUserByUsername(null));
    }
}
