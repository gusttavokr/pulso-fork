package web.pulso.entity.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Usuario;

import static web.pulso.entity.usuario.UsuarioFactory.criarUsuarioValido;

@DisplayName("Testes de email da entidade Usuario")
class UsuarioEmailTest {

    @Test
    @DisplayName("Deve lançar erro quando email for nulo")
    void deveLancarErroQuandoEmailForNulo() {

        Usuario usuario = criarUsuarioValido();

        usuario.setEmail(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                usuario::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email do usuário é obrigatório.");
    }

    @Test
    @DisplayName("Deve lançar erro quando email estiver vazio")
    void deveLancarErroQuandoEmailEstiverVazio() {

        Usuario usuario = criarUsuarioValido();

        usuario.setEmail("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                usuario::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email do usuário é obrigatório.");
    }

    @Test
    @DisplayName("Deve lançar erro quando email possuir formato inválido")
    void deveLancarErroQuandoEmailPossuirFormatoInvalido() {

        Usuario usuario = criarUsuarioValido();

        usuario.setEmail("email.com");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                usuario::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email do usuário possui formato inválido.");
    }

    @Test
    @DisplayName("Deve aceitar email válido")
    void deveAceitarEmailValido() {

        Usuario usuario = criarUsuarioValido();

        usuario.setEmail("usuario@gmail.com");

        assertDoesNotThrow(usuario::validar);
    }

    @Test
    @DisplayName("Deve aceitar email com 255 caracteres")
    void deveAceitarEmailCom255Caracteres() {

        String email = "a".repeat(249) + "@b.com";

        Usuario usuario = criarUsuarioValido();

        usuario.setEmail(email);

        assertDoesNotThrow(usuario::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando email ultrapassar 255 caracteres")
    void deveLancarErroQuandoEmailUltrapassar255Caracteres() {

        String email = "a".repeat(250) + "@b.com";

        Usuario usuario = criarUsuarioValido();

        usuario.setEmail(email);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                usuario::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email do usuário deve possuir no máximo 255 caracteres.");
    }
}
