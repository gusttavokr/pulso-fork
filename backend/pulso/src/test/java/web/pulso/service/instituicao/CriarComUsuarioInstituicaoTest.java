package web.pulso.service.instituicao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static web.pulso.service.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.service.instituicao.InstituicaoRequestDTOFactory.criarInstituicaoRequestDTOValido;
import static web.pulso.service.instituicao.UsuarioFactory.criarUsuarioComInstituicao;
import static web.pulso.service.instituicao.UsuarioFactory.criarUsuarioComProfissional;
import static web.pulso.service.instituicao.UsuarioFactory.criarUsuarioValido;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Usuario;
import web.pulso.exception.business.ConflictException;

@DisplayName("Testes do método criarComUsuario da classe InstituicaoService")
class CriarComUsuarioInstituicaoTest extends InstituicaoServiceBaseTest {

    @Test
    @DisplayName("Deve criar instituição com usuário com sucesso")
    void deveCriarInstituicaoComUsuarioComSucesso() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        Instituicao instituicao = criarInstituicaoValida();

        when(instituicaoMapper.toEntity(dto))
                .thenReturn(instituicao);

        when(instituicaoRepository.save(instituicao))
                .thenReturn(instituicao);

        Instituicao resultado = instituicaoService.criarComUsuario(dto, usuario);

        assertNotNull(resultado);

        assertEquals(usuario, instituicao.getUsuario());

        verify(instituicaoMapper).toEntity(dto);

        verify(instituicaoRepository).save(instituicao);
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário for nulo")
    void deveLancarErroQuandoUsuarioForNulo() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        assertThrows(
                NullPointerException.class,
                () -> instituicaoService.criarComUsuario(dto, null));

        verifyNoInteractions(instituicaoRepository);
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário já possui instituição")
    void deveLancarErroQuandoUsuarioJaPossuiInstituicao() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioComInstituicao();

        assertThrows(
                ConflictException.class,
                () -> instituicaoService.criarComUsuario(dto, usuario));

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário já possui profissional")
    void deveLancarErroQuandoUsuarioJaPossuiProfissional() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioComProfissional();

        assertThrows(
                ConflictException.class,
                () -> instituicaoService.criarComUsuario(dto, usuario));

        verify(instituicaoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro quando mapper retornar entidade nula")
    void deveLancarErroQuandoMapperRetornarNull() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Usuario usuario = criarUsuarioValido();

        when(instituicaoMapper.toEntity(dto))
                .thenReturn(null);

        assertThrows(
                NullPointerException.class,
                () -> instituicaoService.criarComUsuario(dto, usuario));

        verify(instituicaoRepository, never()).save(any());
    }
}
