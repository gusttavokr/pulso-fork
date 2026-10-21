package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.usuario.UsuarioFactory.criarUsuarioValido;
import static web.pulso.service.usuario.UsuarioRequestDTOFactory.criarUsuarioRequestDTOComSenhaVazia;
import static web.pulso.service.usuario.UsuarioRequestDTOFactory.criarUsuarioRequestDTOValido;
import static web.pulso.service.usuario.UsuarioResponseDTOFactory.criarUsuarioResponseDTOValido;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.UsuarioRequestDTO;
import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.entity.Usuario;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método atualizar da classe UsuarioService")
class AtualizarUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve atualizar senha com sucesso")
    void deveAtualizarSenhaComSucesso() {

        Long usuarioId = 1L;

        Usuario usuario = criarUsuarioValido();

        UsuarioRequestDTO dto = criarUsuarioRequestDTOValido();

        UsuarioResponseDTO responseDTO = criarUsuarioResponseDTOValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.encode(dto.getSenha()))
                .thenReturn("novaSenhaEncoded");

        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(responseDTO);

        UsuarioResponseDTO resultado = usuarioService.atualizar(usuarioId, dto);

        assertNotNull(resultado);

        assertEquals("novaSenhaEncoded", usuario.getSenha());

        verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não existir")
    void deveLancarErroQuandoUsuarioNaoExistir() {

        Long usuarioId = 999L;

        UsuarioRequestDTO dto = criarUsuarioRequestDTOValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> usuarioService.atualizar(usuarioId, dto));

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando dto for nulo")
    void deveLancarErroQuandoDtoForNulo() {

        Long usuarioId = 1L;

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(criarUsuarioValido()));

        assertThrows(
                NullPointerException.class,
                () -> usuarioService.atualizar(usuarioId, null));
    }

    @Test
    @DisplayName("Não deve atualizar senha quando senha estiver vazia")
    void naoDeveAtualizarSenhaQuandoSenhaEstiverVazia() {

        Long usuarioId = 1L;

        Usuario usuario = criarUsuarioValido();

        String senhaOriginal = usuario.getSenha();

        UsuarioRequestDTO dto = criarUsuarioRequestDTOComSenhaVazia();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(criarUsuarioResponseDTOValido());

        usuarioService.atualizar(usuarioId, dto);

        assertEquals(senhaOriginal, usuario.getSenha());

        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName("Deve aceitar senha com tamanho mínimo de 6 caracteres")
    void deveAceitarSenhaComTamanhoMinimoDeSeisCaracteres() {

        Long usuarioId = 1L;

        Usuario usuario = criarUsuarioValido();

        UsuarioRequestDTO dto = new UsuarioRequestDTO("usuario@pulso.com", "123456");

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.encode("123456"))
                .thenReturn("senhaMinimaEncoded");

        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(criarUsuarioResponseDTOValido());

        usuarioService.atualizar(usuarioId, dto);

        assertEquals("senhaMinimaEncoded", usuario.getSenha());
    }
}
