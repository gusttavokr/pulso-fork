package web.pulso.service.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static web.pulso.service.usuario.UsuarioFactory.criarUsuarioValido;
import static web.pulso.service.usuario.UsuarioResponseDTOFactory.criarUsuarioResponseDTOValido;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import web.pulso.dto.response.usuario.UsuarioResponseDTO;
import web.pulso.entity.Usuario;

@DisplayName("Testes do método listar da classe UsuarioService")
class ListarUsuarioTest extends UsuarioServiceBaseTest {

    @Test
    @DisplayName("Deve listar usuários com sucesso")
    void deveListarUsuariosComSucesso() {

        Usuario usuario = criarUsuarioValido();

        UsuarioResponseDTO dto = criarUsuarioResponseDTOValido();

        when(usuarioRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(usuario));

        when(usuarioMapper.toResponseDTO(usuario))
                .thenReturn(dto);

        List<UsuarioResponseDTO> resultado = usuarioService.listar(
                true,
                Date.valueOf(LocalDate.now().minusDays(1)),
                Date.valueOf(LocalDate.now()));

        assertNotNull(resultado);

        assertEquals(1, resultado.size());

        assertEquals(dto, resultado.get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver usuários")
    void deveRetornarListaVaziaQuandoNaoHouverUsuarios() {

        when(usuarioRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        List<UsuarioResponseDTO> resultado = usuarioService.listar(null, null, null);

        assertNotNull(resultado);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve aceitar datas iguais como limite")
    void deveAceitarDatasIguaisComoLimite() {

        Date data = Date.valueOf(LocalDate.now());

        when(usuarioRepository.findAll(any(Specification.class)))
                .thenReturn(Collections.emptyList());

        List<UsuarioResponseDTO> resultado = usuarioService.listar(null, data, data);

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Deve lançar erro quando repository retornar nulo")
    void deveLancarErroQuandoRepositoryRetornarNulo() {

        when(usuarioRepository.findAll(any(Specification.class)))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> usuarioService.listar(null, null, null));
    }
}
