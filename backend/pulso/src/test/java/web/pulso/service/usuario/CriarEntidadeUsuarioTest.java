package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Usuario;

@DisplayName("Testes do método criarEntidade da classe UsuarioService")
class CriarEntidadeUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve criar entidade de usuário com sucesso")
    void deveCriarEntidadeUsuarioComSucesso() {

        String email = "usuario@pulso.com";

        String senha = "senha123";

        when(passwordEncoder.encode(senha))
                .thenReturn("senhaEncoded");

        when(usuarioRepository.save(org.mockito.ArgumentMatchers.any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.criarEntidade(email, senha);

        assertNotNull(resultado);

        assertEquals(email, resultado.getEmail());

        assertEquals("senhaEncoded", resultado.getSenha());

        assertTrue(resultado.getAtivo());

        verify(usuarioRepository).save(resultado);
    }

    @Test
    @DisplayName("Deve aceitar email com 255 caracteres")
    void deveAceitarEmailCom255Caracteres() {

        String email = "a".repeat(249) + "@b.com";

        when(passwordEncoder.encode("senha123"))
                .thenReturn("senhaEncoded");

        when(usuarioRepository.save(org.mockito.ArgumentMatchers.any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.criarEntidade(email, "senha123");

        assertEquals(255, resultado.getEmail().length());
    }

    @Test
    @DisplayName("Deve repassar senha nula para o encoder")
    void deveRepassarSenhaNulaParaEncoder() {

        when(passwordEncoder.encode(null))
                .thenReturn("senhaEncoded");

        when(usuarioRepository.save(org.mockito.ArgumentMatchers.any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.criarEntidade("usuario@pulso.com", null);

        assertNotNull(resultado);

        verify(passwordEncoder).encode(null);
    }
}
