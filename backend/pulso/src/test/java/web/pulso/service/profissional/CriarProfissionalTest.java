package web.pulso.service.profissional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.mappers.profissional.ProfissionalRequestDTOFactory.criarProfissionalRequestDTOValido;
import static web.pulso.service.profissional.UsuarioFactory.criarUsuarioValido;
import static web.pulso.service.profissional.ProfissionalResponseDTOFactory.criarProfissionalResponseDTOValido;
import static web.pulso.service.profissional.InstituicaoFactory.criarInstituicaoValida;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;

@DisplayName("Testes do método criar da classe ProfissionalService")
public class CriarProfissionalTest extends ProfissionalServiceBaseTest {

    @Test
    @DisplayName("Deve criar Profissional com sucesso") // fluxo feliz
    void deveCriarProfissionalComSucesso() {

        Long usuarioId = 1L;

        ProfissionalRequestDTO requestDTO = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO responseDTO = criarProfissionalResponseDTOValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(profissionalMapper.toEntity(requestDTO))
                .thenReturn(profissional);

        when(profissionalMapper.toResponseDTO(profissional))
                .thenReturn(responseDTO);

        ProfissionalResponseDTO resultado = profissionalService.criar(requestDTO, usuarioId);

        assertNotNull(resultado);

        assertEquals(usuario, profissional.getUsuario());

        verify(usuarioRepository).findById(usuarioId);

        verify(profissionalMapper).toEntity(requestDTO);

        verify(profissionalRepository).save(profissional);

        verify(profissionalMapper).toResponseDTO(profissional);
    }

    // nao valeria a pena criar o teste para verificar se o dto é nulo porque está
    // sendo usado o @RequestBody e @Valid no controller
    @Test
    @DisplayName("Deve lançar erro quando id do usuário for nulo") // validacao - excecao
    void deveLancarErroQuandoUsuarioIdForNull() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        assertThrows(
                BadRequestException.class,
                () -> profissionalService.criar(dto, null));

        verifyNoInteractions(usuarioRepository);

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não existir") // validacao - excecao
    void deveLancarErroQuandoUsuarioNaoExistir() {

        Long usuarioId = 1L;

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> profissionalService.criar(dto, usuarioId));

        verify(usuarioRepository).findById(usuarioId);

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar erro quando usuário já possui instituição") // relacao - excecao
    void deveLancarErroQuandoUsuarioJaPossuiInstituicao() {

        Long usuarioId = 1L;

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Instituicao instituicao = criarInstituicaoValida();

        usuario.setInstituicao(instituicao);

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        assertThrows(
                ConflictException.class,
                () -> profissionalService.criar(dto, usuarioId));

        verify(usuarioRepository).findById(usuarioId);

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário já possui profissional") // relacao - excecao
    void deveLancarErroQuandoUsuarioJaPossuiProfissional() {

        Long usuarioId = 1L;

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Profissional profissionalExistente = criarProfissionalValido();

        usuario.setProfissional(profissionalExistente);

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        assertThrows(
                ConflictException.class,
                () -> profissionalService.criar(dto, usuarioId));

        verify(usuarioRepository).findById(usuarioId);

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando dto for nulo")
    void deveLancarErroQuandoDtoForNulo() {

        Long usuarioId = 1L;

        Usuario usuario = criarUsuarioValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        assertThrows(
                Exception.class,
                () -> profissionalService.criar(null, usuarioId));

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar entidade nula")
    void deveLancarErroQuandoMapperRetornarNull() {

        Long usuarioId = 1L;

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(profissionalMapper.toEntity(dto))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.criar(dto, usuarioId));

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário estiver inativo")
    void deveLancarErroQuandoUsuarioEstiverInativo() {

        Long usuarioId = 1L;

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        usuario.setAtivo(false);

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO responseDTO = criarProfissionalResponseDTOValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(profissionalMapper.toEntity(dto))
                .thenReturn(profissional);

        when(profissionalMapper.toResponseDTO(profissional))
                .thenReturn(responseDTO);

        assertThrows(
                Exception.class,
                () -> profissionalService.criar(dto, usuarioId));
    }

    @Test
    @DisplayName("Deve lançar erro quando save retornar nulo")
    void deveLancarErroQuandoSaveRetornarNulo() {

        Long usuarioId = 1L;

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Profissional profissional = criarProfissionalValido();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        when(profissionalMapper.toEntity(dto))
                .thenReturn(profissional);

        when(profissionalRepository.save(profissional))
                .thenReturn(null);

        assertThrows(
                Exception.class,
                () -> profissionalService.criar(dto, usuarioId));
    }
}
