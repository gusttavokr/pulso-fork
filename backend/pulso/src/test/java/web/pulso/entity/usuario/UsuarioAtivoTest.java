package web.pulso.entity.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Usuario;

import static web.pulso.entity.usuario.UsuarioFactory.criarUsuarioValido;

@DisplayName("Testes de status da entidade Usuario")
class UsuarioAtivoTest {

    @Test
    @DisplayName("Deve lançar erro quando status ativo for nulo")
    void deveLancarErroQuandoStatusAtivoForNulo() {

        Usuario usuario = criarUsuarioValido();

        usuario.setAtivo(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                usuario::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O status do usuário é obrigatório.");
    }
}