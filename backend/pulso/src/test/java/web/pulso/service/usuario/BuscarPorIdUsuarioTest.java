package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.service.usuario.UsuarioFactory.criarUsuarioValido;
import static web.pulso.service.usuario.UsuarioResponseDTOFactory.criarUsuarioResponseDTOValido;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.entity.Usuario;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método buscarPorId da classe UsuarioService")
class BuscarPorIdUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve buscar usuário por id com sucesso")
    void deveBuscarUsuarioPorIdComSucesso() {

        Long id = 1L;

        Usuario usuario = criarUsuarioValido();

        UsuarioResponseDTO dto = criarUsuarioResponseDTOValido();

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuario));

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(dto);

        UsuarioResponseDTO resultado = usuarioService.buscarPorId(id);

        assertNotNull(resultado);

        assertEquals(dto, resultado);
    }

    @Test
    @DisplayName("Deve lançar erro quando id for nulo")
    void deveLancarErroQuandoIdForNulo() {

        when(usuarioRepository.findById(null))
                .thenThrow(new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.buscarPorId(null));

        verifyNoInteractions(usuarioMapper);
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não existir")
    void deveLancarErroQuandoUsuarioNaoExistir() {

        Long id = 999L;

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> usuarioService.buscarPorId(id));

        verifyNoInteractions(usuarioMapper);
    }

    @Test
    @DisplayName("Deve retornar nulo quando mapper retornar dto nulo")
    void deveRetornarNuloQuandoMapperRetornarDtoNulo() {

        Long id = 1L;

        Usuario usuario = criarUsuarioValido();

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuario));

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(null);

        UsuarioResponseDTO resultado = usuarioService.buscarPorId(id);

        assertNull(resultado);
    }
}
