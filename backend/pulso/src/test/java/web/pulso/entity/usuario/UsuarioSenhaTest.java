package web.pulso.entity.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Usuario;

import static web.pulso.entity.usuario.UsuarioFactory.criarUsuarioValido;

@DisplayName("Testes de senha da entidade Usuario")
class UsuarioSenhaTest {

    @Test
    @DisplayName("Deve lançar erro quando senha for nula")
    void deveLancarErroQuandoSenhaForNula() {

        Usuario usuario = criarUsuarioValido();

        usuario.setSenha(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                usuario::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A senha do usuário é obrigatória.");
    }

    @Test
    @DisplayName("Deve lançar erro quando senha possuir menos de 8 caracteres")
    void deveLancarErroQuandoSenhaPossuirMenosDeOitoCaracteres() {

        Usuario usuario = criarUsuarioValido();

        usuario.setSenha("1234567");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                usuario::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A senha do usuário deve possuir pelo menos 8 caracteres.");
    }

    @Test
    @DisplayName("Deve aceitar senha com 8 caracteres")
    void deveAceitarSenhaComOitoCaracteres() {

        Usuario usuario = criarUsuarioValido();

        usuario.setSenha("12345678");

        assertDoesNotThrow(usuario::validar);
    }
}