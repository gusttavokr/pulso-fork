package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.usuario.UsuarioFactory.criarUsuarioValido;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import web.pulso.auth.UsuarioAutenticado;
import web.pulso.dto.response.usuario.UsuarioLoginResponseDTO;
import web.pulso.entity.Usuario;

@DisplayName("Testes do método autenticar da classe UsuarioService")
class AutenticarUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve autenticar usuário com sucesso")
    void deveAutenticarUsuarioComSucesso() {

        String email = "usuario@pulso.com";

        String senha = "senha123";

        Usuario usuario = criarUsuarioValido();

        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);

        UsuarioLoginResponseDTO responseDTO = new UsuarioLoginResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setToken("token.jwt");

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(new UsuarioAutenticado(usuario));

        when(jwtService.generateToken(authentication))
                .thenReturn("token.jwt");

        when(usuarioMapper.toLoginResponseDTO(usuario, "token.jwt"))
                .thenReturn(responseDTO);

        UsuarioLoginResponseDTO resultado = usuarioService.autenticar(email, senha);

        assertNotNull(resultado);

        assertEquals("token.jwt", resultado.getToken());

        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Deve lançar erro quando autenticação falhar")
    void deveLancarErroQuandoAutenticacaoFalhar() {

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Credenciais inválidas"));

        assertThrows(
                RuntimeException.class,
                () -> usuarioService.autenticar("usuario@pulso.com", "senhaErrada"));
    }

    @Test
    @DisplayName("Deve lançar erro quando principal não for UsuarioAutenticado")
    void deveLancarErroQuandoPrincipalNaoForUsuarioAutenticado() {

        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn("principal-invalido");

        assertThrows(
                ClassCastException.class,
                () -> usuarioService.autenticar("usuario@pulso.com", "senha123"));
    }
}
