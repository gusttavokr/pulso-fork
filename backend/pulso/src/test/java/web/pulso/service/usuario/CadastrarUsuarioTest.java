package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.usuario.CadastroRequestDTOFactory.criarCadastroComDoisPerfis;
import static web.pulso.service.usuario.CadastroRequestDTOFactory.criarCadastroComInstituicaoValido;
import static web.pulso.service.usuario.CadastroRequestDTOFactory.criarCadastroComProfissionalValido;
import static web.pulso.service.usuario.CadastroRequestDTOFactory.criarCadastroSemPerfil;
import static web.pulso.service.usuario.UsuarioResponseDTOFactory.criarUsuarioResponseDTOValido;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.CadastroRequestDTO;
import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;
import web.pulso.exception.business.BadRequestException;

@DisplayName("Testes do método cadastrar da classe UsuarioService")
class CadastrarUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve cadastrar usuário com instituição com sucesso")
    void deveCadastrarUsuarioComInstituicaoComSucesso() {

        CadastroRequestDTO dto = criarCadastroComInstituicaoValido();

        UsuarioResponseDTO responseDTO = criarUsuarioResponseDTOValido();

        when(passwordEncoder.encode(dto.getSenha()))
                .thenReturn("senhaEncoded");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(instituicaoService.criarComUsuario(any(), any()))
                .thenReturn(new Instituicao());

        when(usuarioMapper.toResponseDTO(any(Usuario.class)))
                .thenReturn(responseDTO);

        UsuarioResponseDTO resultado = usuarioService.cadastrar(dto);

        assertNotNull(resultado);

        verify(instituicaoService).criarComUsuario(any(), any());

        verify(profissionalService, never()).criarComUsuario(any(), any());
    }

    @Test
    @DisplayName("Deve cadastrar usuário com profissional com sucesso")
    void deveCadastrarUsuarioComProfissionalComSucesso() {

        CadastroRequestDTO dto = criarCadastroComProfissionalValido();

        when(passwordEncoder.encode(dto.getSenha()))
                .thenReturn("senhaEncoded");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(profissionalService.criarComUsuario(any(), any()))
                .thenReturn(new Profissional());

        when(usuarioMapper.toResponseDTO(any(Usuario.class)))
                .thenReturn(criarUsuarioResponseDTOValido());

        UsuarioResponseDTO resultado = usuarioService.cadastrar(dto);

        assertNotNull(resultado);

        verify(profissionalService).criarComUsuario(any(), any());

        verify(instituicaoService, never()).criarComUsuario(any(), any());
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional e instituição forem nulos")
    void deveLancarErroQuandoProfissionalEInstituicaoForemNulos() {

        CadastroRequestDTO dto = criarCadastroSemPerfil();

        when(passwordEncoder.encode(dto.getSenha()))
                .thenReturn("senhaEncoded");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(
                BadRequestException.class,
                () -> usuarioService.cadastrar(dto));
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional e instituição forem informados simultaneamente")
    void deveLancarErroQuandoProfissionalEInstituicaoForemInformados() {

        CadastroRequestDTO dto = criarCadastroComDoisPerfis();

        when(passwordEncoder.encode(dto.getSenha()))
                .thenReturn("senhaEncoded");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(
                BadRequestException.class,
                () -> usuarioService.cadastrar(dto));

        verify(profissionalService, never()).criarComUsuario(any(), any());

        verify(instituicaoService, never()).criarComUsuario(any(), any());
    }

    @Test
    @DisplayName("Deve lançar erro quando dto for nulo")
    void deveLancarErroQuandoDtoForNulo() {

        assertThrows(
                NullPointerException.class,
                () -> usuarioService.cadastrar(null));
    }
}
