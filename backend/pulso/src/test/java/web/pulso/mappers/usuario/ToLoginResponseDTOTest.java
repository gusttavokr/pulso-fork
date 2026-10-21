package web.pulso.mappers.usuario;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.usuario.UsuarioFactory.criarUsuarioValido;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.dto.response.instituicao.InstituicaoSimpleLoginResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleLoginResponseDTO;
import web.pulso.dto.response.usuario.UsuarioLoginResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.TipoUsuario;
import web.pulso.service.mappers.InstituicaoMapper;
import web.pulso.service.mappers.ProfissionalMapper;
import web.pulso.service.mappers.UsuarioMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do método toLoginResponseDTO da classe UsuarioMapper")
public class ToLoginResponseDTOTest {

    @Mock
    private InstituicaoMapper instituicaoMapper;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @InjectMocks
    private UsuarioMapper mapper;

    @Test
    @DisplayName("Deve retornar null quando usuário for nulo")
    void deveRetornarNullQuandoUsuarioForNulo() {

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(null, "token");

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve retornar null quando token for nulo")
    void deveRetornarNullQuandoTokenForNulo() {

        Usuario usuario = criarUsuarioValido();

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos básicos corretamente")
    void deveMapearCamposBasicosCorretamente() {

        Usuario usuario = criarUsuarioValido();

        usuario.setId(1L);

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token123");

        assertThat(dto.getId())
                .isEqualTo(usuario.getId());

        assertThat(dto.getToken())
                .isEqualTo("token123");
    }

    @Test
    @DisplayName("Deve definir tipo INSTITUICAO")
    void deveDefinirTipoInstituicao() {

        Usuario usuario = criarUsuarioValido();

        usuario.setInstituicao(new Instituicao());

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getTipo())
                .isEqualTo(TipoUsuario.INSTITUICAO);
    }

    @Test
    @DisplayName("Deve definir tipo PROFISSIONAL")
    void deveDefinirTipoProfissional() {

        Usuario usuario = criarUsuarioValido();

        usuario.setProfissional(new Profissional());

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getTipo())
                .isEqualTo(TipoUsuario.PROFISSIONAL);
    }

    @Test
    @DisplayName("Deve definir tipo DESCONHECIDO")
    void deveDefinirTipoDesconhecido() {

        Usuario usuario = criarUsuarioValido();

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getTipo())
                .isEqualTo(TipoUsuario.DESCONHECIDO);
    }

    @Test
    @DisplayName("Deve adicionar role INSTITUICAO")
    void deveAdicionarRoleInstituicao() {

        Usuario usuario = criarUsuarioValido();

        usuario.setInstituicao(new Instituicao());

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getRoles())
                .containsExactly("INSTITUICAO");
    }

    @Test
    @DisplayName("Deve adicionar role PROFISSIONAL")
    void deveAdicionarRoleProfissional() {

        Usuario usuario = criarUsuarioValido();

        usuario.setProfissional(new Profissional());

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getRoles())
                .containsExactly("PROFISSIONAL");
    }

    @Test
    @DisplayName("Deve adicionar role LIDER_ESCALA")
    void deveAdicionarRoleLiderEscala() {

        Usuario usuario = criarUsuarioValido();

        Profissional profissional = new Profissional();

        profissional.setInstituicoesLiderEscala(List.of(new Instituicao()));

        usuario.setProfissional(profissional);

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getRoles())
                .containsExactly(
                        "PROFISSIONAL",
                        "LIDER_ESCALA");
    }

    @Test
    @DisplayName("Deve mapear instituição utilizando InstituicaoMapper")
    void deveMapearInstituicaoUtilizandoInstituicaoMapper() {

        Usuario usuario = criarUsuarioValido();

        Instituicao instituicao = new Instituicao();

        usuario.setInstituicao(instituicao);

        InstituicaoSimpleLoginResponseDTO instituicaoDTO =
                new InstituicaoSimpleLoginResponseDTO();

        when(instituicaoMapper.toSimpleLoginResponseDTO(instituicao))
                .thenReturn(instituicaoDTO);

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getInstituicao())
                .isEqualTo(instituicaoDTO);

        verify(instituicaoMapper)
                .toSimpleLoginResponseDTO(instituicao);
    }

    @Test
    @DisplayName("Deve mapear profissional utilizando ProfissionalMapper")
    void deveMapearProfissionalUtilizandoProfissionalMapper() {

        Usuario usuario = criarUsuarioValido();

        Profissional profissional = new Profissional();

        usuario.setProfissional(profissional);

        ProfissionalSimpleLoginResponseDTO profissionalDTO =
                new ProfissionalSimpleLoginResponseDTO();

        when(profissionalMapper.toSimpleLoginResponseDTO(profissional))
                .thenReturn(profissionalDTO);

        UsuarioLoginResponseDTO dto = mapper.toLoginResponseDTO(usuario, "token");

        assertThat(dto.getProfissional())
                .isEqualTo(profissionalDTO);

        verify(profissionalMapper)
                .toSimpleLoginResponseDTO(profissional);
    }

}