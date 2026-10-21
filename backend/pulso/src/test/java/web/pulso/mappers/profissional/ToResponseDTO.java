package web.pulso.mappers.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.dto.response.troca.TrocaSimpleResponseDTO;
import web.pulso.dto.response.usuario.UsuarioSimpleResponseDTO;
import web.pulso.entity.Escala;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.UF;
import web.pulso.service.mappers.EscalaMapper;
import web.pulso.service.mappers.InstituicaoMapper;
import web.pulso.service.mappers.PlantaoMapper;
import web.pulso.service.mappers.ProfissionalMapper;
import web.pulso.service.mappers.TrocaMapper;
import web.pulso.service.mappers.UsuarioMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do método toResponseDTO da classe ProfissionalMapper")
public class ToResponseDTO {

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private InstituicaoMapper instituicaoMapper;

    @Mock
    private EscalaMapper escalaMapper;

    @Mock
    private PlantaoMapper plantaoMapper;

    @Mock
    private TrocaMapper trocaMapper;

    @InjectMocks
    private ProfissionalMapper mapper;

    @Test
    @DisplayName("Deve retornar null quando profissional for nulo")
    void deveRetornarNullQuandoProfissionalForNulo() {

        ProfissionalResponseDTO dto = mapper.toResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Profissional profissional = criarProfissionalValido();

        profissional.setId(1L);

        profissional.setFotoPerfil("foto.png");

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getId())
                .isEqualTo(profissional.getId());

        assertThat(dto.getNome())
                .isEqualTo(profissional.getNome());

        assertThat(dto.getTelefone())
                .isEqualTo(profissional.getTelefone());

        assertThat(dto.getFotoPerfil())
                .isEqualTo(profissional.getFotoPerfil());

        assertThat(dto.getAnoFormacao())
                .isEqualTo(profissional.getAnoFormacao());

        assertThat(dto.getPix())
                .isEqualTo(profissional.getPix());

        assertThat(dto.getTipoPix())
                .isEqualTo(profissional.getTipoPix());

        assertThat(dto.getDataNasc())
                .isEqualTo(profissional.getDataNasc());
    }

    @Test
    @DisplayName("Deve mapear emails corretamente")
    void deveMapearEmailsCorretamente() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getEmails())
                .containsExactlyElementsOf(
                        profissional.getEmails());
    }

    @Test
    @DisplayName("Deve mapear especialidades corretamente")
    void deveMapearEspecialidadesCorretamente() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getEspecialidades())
                .containsExactlyInAnyOrderElementsOf(
                        profissional.getEspecialidades());
    }

    @Test
    @DisplayName("Deve formatar CRMs corretamente")
    void deveFormatarCrmsCorretamente() {

        Profissional profissional = criarProfissionalValido();

        profissional.setCrmNumero("12345");

        profissional.setCrms(Set.of(UF.RN, UF.PB));

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getCrmsFormatados())
                .containsExactlyInAnyOrder(
                        "12345/RN",
                        "12345/PB");
    }

    @Test
    @DisplayName("Deve criar lista vazia quando emails forem nulos")
    void deveCriarListaVaziaQuandoEmailsForemNulos() {

        Profissional profissional = criarProfissionalValido();

        profissional.setEmails(null);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getEmails())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar lista vazia quando especialidades forem nulas")
    void deveCriarListaVaziaQuandoEspecialidadesForemNulas() {

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(null);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getEspecialidades())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar lista vazia quando CRMs forem nulos")
    void deveCriarListaVaziaQuandoCrmsForemNulos() {

        Profissional profissional = criarProfissionalValido();

        profissional.setCrms(null);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getCrmsFormatados())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de emails")
    void deveCriarNovaInstanciaDaListaDeEmails() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getEmails())
                .isNotSameAs(profissional.getEmails());

        assertThat(dto.getEmails())
                .containsExactlyElementsOf(
                        profissional.getEmails());
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de especialidades")
    void deveCriarNovaInstanciaDaListaDeEspecialidades() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getEspecialidades())
                .isNotSameAs(profissional.getEspecialidades());

        assertThat(dto.getEspecialidades())
                .containsExactlyInAnyOrderElementsOf(
                        profissional.getEspecialidades());
    }

    @Test
    @DisplayName("Deve criar nova instância de DTO")
    void deveCriarNovaInstanciaDeDto() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto)
                .isNotNull()
                .isNotSameAs(profissional);
    }

    @Test
    @DisplayName("Não deve alterar emails do profissional ao modificar emails do DTO")
    void naoDeveAlterarEmailsDoProfissionalAoModificarEmailsDoDto() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        dto.getEmails()
                .add("novoemail@gmail.com");

        assertThat(profissional.getEmails())
                .doesNotContain("novoemail@gmail.com");

        assertThat(dto.getEmails())
                .contains("novoemail@gmail.com");
    }

    @Test
    @DisplayName("Deve mapear usuário utilizando UsuarioMapper")
    void deveMapearUsuarioUtilizandoUsuarioMapper() {

        Profissional profissional = criarProfissionalValido();

        Usuario usuario = new Usuario();

        profissional.setUsuario(usuario);

        UsuarioSimpleResponseDTO usuarioDTO = new UsuarioSimpleResponseDTO();

        when(usuarioMapper.toSimpleResponseDTO(usuario))
                .thenReturn(usuarioDTO);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getUsuario())
                .isEqualTo(usuarioDTO);

        verify(usuarioMapper)
                .toSimpleResponseDTO(usuario);
    }

    @Test
    @DisplayName("Deve mapear instituições CLT utilizando InstituicaoMapper")
    void deveMapearInstituicoesCltUtilizandoInstituicaoMapper() {

        Profissional profissional = criarProfissionalValido();

        Instituicao instituicao = new Instituicao();

        profissional.setInstituicoesClt(List.of(instituicao));

        InstituicaoSimpleResponseDTO instituicaoDTO = new InstituicaoSimpleResponseDTO();

        when(instituicaoMapper.toSimpleResponseDTO(instituicao))
                .thenReturn(instituicaoDTO);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getInstituicoesClt())
                .containsExactly(instituicaoDTO);

        verify(instituicaoMapper)
                .toSimpleResponseDTO(instituicao);
    }

    @Test
    @DisplayName("Deve mapear escalas utilizando EscalaMapper")
    void deveMapearEscalasUtilizandoEscalaMapper() {

        Profissional profissional = criarProfissionalValido();

        Escala escala = new Escala();

        profissional.setEscalas(List.of(escala));

        EscalaSimpleResponseDTO escalaDTO = new EscalaSimpleResponseDTO();

        when(escalaMapper.toSimpleResponseDTO(escala))
                .thenReturn(escalaDTO);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getEscalas())
                .containsExactly(escalaDTO);

        verify(escalaMapper)
                .toSimpleResponseDTO(escala);
    }

    @Test
    @DisplayName("Deve mapear plantões combinados utilizando PlantaoMapper")
    void deveMapearPlantoesCombinadosUtilizandoPlantaoMapper() {

        Profissional profissional = criarProfissionalValido();

        Plantao plantao = new Plantao();

        profissional.setPlantoesCombinados(List.of(plantao));

        PlantaoSimpleResponseDTO plantaoDTO = new PlantaoSimpleResponseDTO();

        when(plantaoMapper.toSimpleResponseDTO(plantao))
                .thenReturn(plantaoDTO);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getPlantoesCombinados())
                .containsExactly(plantaoDTO);

        verify(plantaoMapper)
                .toSimpleResponseDTO(plantao);
    }

    @Test
    @DisplayName("Deve mapear trocas de origem utilizando TrocaMapper")
    void deveMapearTrocasDeOrigemUtilizandoTrocaMapper() {

        Profissional profissional = criarProfissionalValido();

        Troca troca = new Troca();

        profissional.setTrocasOrigem(List.of(troca));

        TrocaSimpleResponseDTO trocaDTO = new TrocaSimpleResponseDTO();

        when(trocaMapper.toSimpleResponseDTO(troca))
                .thenReturn(trocaDTO);

        ProfissionalResponseDTO dto = mapper.toResponseDTO(profissional);

        assertThat(dto.getTrocasOrigem())
                .containsExactly(trocaDTO);

        verify(trocaMapper)
                .toSimpleResponseDTO(troca);
    }

}
