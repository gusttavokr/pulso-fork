package web.pulso.service.profissional;

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
import static web.pulso.service.profissional.UsuarioFactory.criarUsuarioComInstituicao;
import static web.pulso.service.profissional.UsuarioFactory.criarUsuarioValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.ConflictException;

@DisplayName("Testes do método criarComUsuario da classe ProfissionalService")
public class CriarComUsuarioProfissionalTest extends ProfissionalServiceBaseTest {

    @Test
    @DisplayName("Deve criar profissional com usuário com sucesso")
    void deveCriarProfissionalComUsuarioComSucesso() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Profissional profissional = criarProfissionalValido();

        when(profissionalMapper.toEntity(dto))
                .thenReturn(profissional);

        when(profissionalRepository.save(profissional))
                .thenReturn(profissional);

        Profissional resultado = profissionalService.criarComUsuario(dto, usuario);

        assertNotNull(resultado);

        assertEquals(usuario, profissional.getUsuario());

        verify(profissionalMapper).toEntity(dto);

        verify(profissionalRepository).save(profissional);
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário for nulo")
    void deveLancarErroQuandoUsuarioForNulo() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        assertThrows(
                BadRequestException.class,
                () -> profissionalService.criarComUsuario(dto, null));

        verifyNoInteractions(profissionalRepository);

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário já possui profissional")
    void deveLancarErroQuandoUsuarioJaPossuiProfissional() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        usuario.setProfissional(
                criarProfissionalValido());

        assertThrows(
                ConflictException.class,
                () -> profissionalService.criarComUsuario(dto, usuario));

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário já possui instituição")
    void deveLancarErroQuandoUsuarioJaPossuiInstituicao() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioComInstituicao();

        assertThrows(
                ConflictException.class,
                () -> profissionalService.criarComUsuario(dto, usuario));

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando dto for nulo")
    void deveLancarErroQuandoDtoForNulo() {

        Usuario usuario = criarUsuarioValido();

        assertThrows(
                Exception.class,
                () -> profissionalService.criarComUsuario(null, usuario));

        verify(profissionalRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar entidade nula")
    void deveLancarErroQuandoMapperRetornarNull() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        when(profissionalMapper.toEntity(dto))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> profissionalService.criarComUsuario(dto, usuario));

        verify(profissionalRepository, never()).save(any());
    }

    // atualmente pode criar um profissional para um usuario inativo
    @Test
    @DisplayName("Deve lançar erro quando usuário estiver inativo")
    void deveLancarErroQuandoUsuarioEstiverInativo() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        usuario.setAtivo(false);

        Profissional profissional = criarProfissionalValido();

        when(profissionalMapper.toEntity(dto))
                .thenReturn(profissional);

        when(profissionalRepository.save(profissional))
                .thenReturn(profissional);

        assertThrows(
                Exception.class,
                () -> profissionalService.criarComUsuario(dto, usuario));
    }

    @Test
    @DisplayName("Deve lançar erro quando save retornar nulo")
    void deveLancarErroQuandoSaveRetornarNulo() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Profissional profissional = criarProfissionalValido();

        when(profissionalMapper.toEntity(dto))
                .thenReturn(profissional);

        when(profissionalRepository.save(profissional))
                .thenReturn(null);

        assertThrows(
                Exception.class,
                () -> profissionalService.criarComUsuario(dto, usuario));

        verify(profissionalRepository).save(profissional);
    }
}
