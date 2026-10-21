package web.pulso.mappers.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.dto.response.setor.SetorSimpleResponseDTO;
import web.pulso.dto.response.usuario.UsuarioSimpleResponseDTO;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.entity.Usuario;
import web.pulso.service.mappers.InstituicaoMapper;
import web.pulso.service.mappers.ProfissionalMapper;
import web.pulso.service.mappers.SetorMapper;
import web.pulso.service.mappers.UsuarioMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do método toResponseDTO da classe InstituicaoMapper")
public class ToResponseDTOTest {

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private SetorMapper setorMapper;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @InjectMocks
    private InstituicaoMapper mapper;

    @Test
    @DisplayName("Deve retornar null quando instituição for nula")
    void deveRetornarNullQuandoInstituicaoForNula() {

        InstituicaoResponseDTO dto = mapper.toResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        var instituicao = criarInstituicaoValida();

        instituicao.setId(1L);

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getId())
                .isEqualTo(instituicao.getId());

        assertThat(dto.getNome())
                .isEqualTo(instituicao.getNome());

        assertThat(dto.getTelefone())
                .isEqualTo(instituicao.getTelefone());

        assertThat(dto.getFotoPerfil())
                .isEqualTo(instituicao.getFotoPerfil());

        assertThat(dto.getEndereco())
                .isEqualTo(instituicao.getEndereco());

        assertThat(dto.getCnpj())
                .isEqualTo(instituicao.getCnpj());

        assertThat(dto.getExigeVinculo())
                .isEqualTo(instituicao.getExigeVinculo());
    }

    @Test
    @DisplayName("Deve mapear emails corretamente")
    void deveMapearEmailsCorretamente() {

        var instituicao = criarInstituicaoValida();

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getEmails())
                .containsExactlyElementsOf(instituicao.getEmails());
    }

    @Test
    @DisplayName("Deve criar lista vazia quando emails forem nulos")
    void deveCriarListaVaziaQuandoEmailsForemNulos() {

        var instituicao = criarInstituicaoValida();

        instituicao.setEmails(null);

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getEmails())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de emails")
    void deveCriarNovaInstanciaDaListaDeEmails() {

        var instituicao = criarInstituicaoValida();

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getEmails())
                .isNotSameAs(instituicao.getEmails());

        assertThat(dto.getEmails())
                .containsExactlyElementsOf(instituicao.getEmails());
    }

    @Test
    @DisplayName("Deve mapear usuário utilizando UsuarioMapper")
    void deveMapearUsuarioUtilizandoUsuarioMapper() {

        var instituicao = criarInstituicaoValida();

        Usuario usuario = new Usuario();

        instituicao.setUsuario(usuario);

        UsuarioSimpleResponseDTO usuarioDTO = new UsuarioSimpleResponseDTO();

        when(usuarioMapper.toSimpleResponseDTO(usuario))
                .thenReturn(usuarioDTO);

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getUsuario())
                .isEqualTo(usuarioDTO);

        verify(usuarioMapper)
                .toSimpleResponseDTO(usuario);
    }

    @Test
    @DisplayName("Deve mapear setores utilizando SetorMapper")
    void deveMapearSetoresUtilizandoSetorMapper() {

        var instituicao = criarInstituicaoValida();

        Setor setor = new Setor();

        instituicao.setSetores(List.of(setor));

        SetorSimpleResponseDTO setorDTO = new SetorSimpleResponseDTO();

        when(setorMapper.toSimpleResponseDTO(setor))
                .thenReturn(setorDTO);

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getSetores())
                .containsExactly(setorDTO);

        verify(setorMapper)
                .toSimpleResponseDTO(setor);
    }

    @Test
    @DisplayName("Deve mapear profissionais CLT utilizando ProfissionalMapper")
    void deveMapearProfissionaisCltUtilizandoProfissionalMapper() {

        var instituicao = criarInstituicaoValida();

        Profissional profissional = new Profissional();

        instituicao.setProfissionais_clt(List.of(profissional));

        ProfissionalSimpleResponseDTO profissionalDTO = new ProfissionalSimpleResponseDTO();

        when(profissionalMapper.toSimpleResponseDTO(profissional))
                .thenReturn(profissionalDTO);

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getProfissionaisClt())
                .containsExactly(profissionalDTO);

        verify(profissionalMapper)
                .toSimpleResponseDTO(profissional);
    }

    @Test
    @DisplayName("Deve mapear profissionais PJ utilizando ProfissionalMapper")
    void deveMapearProfissionaisPjUtilizandoProfissionalMapper() {

        var instituicao = criarInstituicaoValida();

        Profissional profissional = new Profissional();

        instituicao.setProfisionais_pj(List.of(profissional));

        ProfissionalSimpleResponseDTO profissionalDTO = new ProfissionalSimpleResponseDTO();

        when(profissionalMapper.toSimpleResponseDTO(profissional))
                .thenReturn(profissionalDTO);

        InstituicaoResponseDTO dto = mapper.toResponseDTO(instituicao);

        assertThat(dto.getProfissionaisPj())
                .containsExactly(profissionalDTO);

        verify(profissionalMapper)
                .toSimpleResponseDTO(profissional);
    }

}