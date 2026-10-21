package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.InstituicaoRequestDTOFactory.criarInstituicaoRequestDTOValido;
import static web.pulso.service.instituicao.InstituicaoResponseDTOFactory.criarInstituicaoResponseDTOValido;
import static web.pulso.service.instituicao.UsuarioFactory.criarUsuarioComInstituicao;
import static web.pulso.service.instituicao.UsuarioFactory.criarUsuarioComProfissional;
import static web.pulso.service.instituicao.UsuarioFactory.criarUsuarioValido;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Usuario;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método criar da classe InstituicaoService")
class CriarInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve criar instituição com sucesso")
    void deveCriarInstituicaoComSucesso() {

        Long usuarioId = 1L;

        InstituicaoRequestDTO requestDTO = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Instituicao instituicao = criarInstituicaoValida();

        InstituicaoResponseDTO responseDTO = criarInstituicaoResponseDTOValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(instituicaoMapper.toEntity(requestDTO))
                .thenReturn(instituicao);

        when(instituicaoRepository.save(instituicao))
                .thenReturn(instituicao);

        when(instituicaoMapper.toResponseDTO(instituicao))
                .thenReturn(responseDTO);

        InstituicaoResponseDTO resultado = instituicaoService.criar(requestDTO, usuarioId);

        assertNotNull(resultado);

        assertEquals(usuario, instituicao.getUsuario());

        verify(usuarioRepository).findById(usuarioId);

        verify(instituicaoMapper).toEntity(requestDTO);

        verify(instituicaoRepository).save(instituicao);

        verify(instituicaoMapper).toResponseDTO(instituicao);
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não existir")
    void deveLancarErroQuandoUsuarioNaoExistir() {

        Long usuarioId = 999L;

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> instituicaoService.criar(dto, usuarioId));

        verify(usuarioRepository).findById(usuarioId);

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário já possui instituição")
    void deveLancarErroQuandoUsuarioJaPossuiInstituicao() {

        Long usuarioId = 1L;

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioComInstituicao();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        assertThrows(
                ConflictException.class,
                () -> instituicaoService.criar(dto, usuarioId));

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário já possui profissional")
    void deveLancarErroQuandoUsuarioJaPossuiProfissional() {

        Long usuarioId = 1L;

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioComProfissional();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        assertThrows(
                ConflictException.class,
                () -> instituicaoService.criar(dto, usuarioId));

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando id do usuário for nulo")
    void deveLancarErroQuandoUsuarioIdForNulo() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        when(usuarioRepository.findById(null))
                .thenThrow(new IllegalArgumentException("ID inválido"));

        assertThrows(
                IllegalArgumentException.class,
                () -> instituicaoService.criar(dto, null));

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar entidade nula")
    void deveLancarErroQuandoMapperRetornarNull() {

        Long usuarioId = 1L;

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(instituicaoMapper.toEntity(dto))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> instituicaoService.criar(dto, usuarioId));

        verify(instituicaoRepository, never()).save(any());
    }
}
