package web.pulso.mappers.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.profissional.ProfissionalSimpleLoginResponseDTO;
import web.pulso.entity.Profissional;
import web.pulso.entity.enums.Especialidade;
import web.pulso.service.mappers.ProfissionalMapper;

@DisplayName("Testes do método toSimpleLoginResponseDTO da classe ProfissionalMapper")
public class ToSimpleLoginResponseDTOTest {

    private ProfissionalMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new ProfissionalMapper(null, null, null, null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando profissional for nulo")
    void deveRetornarNullQuandoProfissionalForNulo() {

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Profissional profissional = criarProfissionalValido();

        profissional.setId(1L);

        profissional.setFotoPerfil("foto.png");

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        assertThat(dto.getId())
                .isEqualTo(profissional.getId());

        assertThat(dto.getNome())
                .isEqualTo(profissional.getNome());

        assertThat(dto.getFotoPerfil())
                .isEqualTo(profissional.getFotoPerfil());
    }

    @Test
    @DisplayName("Deve mapear especialidades corretamente")
    void deveMapearEspecialidadesCorretamente() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        assertThat(dto.getEspecialidades())
                .containsExactlyInAnyOrderElementsOf(
                        profissional.getEspecialidades());
    }

    @Test
    @DisplayName("Deve criar lista vazia quando especialidades forem nulas")
    void deveCriarListaVaziaQuandoEspecialidadesForemNulas() {

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(null);

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        assertThat(dto.getEspecialidades())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de especialidades")
    void deveCriarNovaInstanciaDaListaDeEspecialidades() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

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

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        assertThat(dto)
                .isNotNull();

        assertThat(dto)
                .isNotSameAs(profissional);
    }

    @Test
    @DisplayName("Não deve alterar especialidades do profissional ao modificar especialidades do DTO")
    void naoDeveAlterarEspecialidadesDoProfissionalAoModificarEspecialidadesDoDto() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        dto.getEspecialidades()
                .add(Especialidade.NEUROLOGIA);

        assertThat(profissional.getEspecialidades())
                .doesNotContain(Especialidade.NEUROLOGIA);

        assertThat(dto.getEspecialidades())
                .contains(Especialidade.NEUROLOGIA);
    }

    @Test
    @DisplayName("Deve mapear foto de perfil nula")
    void deveMapearFotoDePerfilNula() {

        Profissional profissional = criarProfissionalValido();

        profissional.setFotoPerfil(null);

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        assertThat(dto.getFotoPerfil())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear nome nulo")
    void deveMapearNomeNulo() {

        Profissional profissional = criarProfissionalValido();

        profissional.setNome(null);

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        assertThat(dto.getNome())
                .isNull();
    }

    @Test
    @DisplayName("Deve mapear id nulo")
    void deveMapearIdNulo() {

        Profissional profissional = criarProfissionalValido();

        profissional.setId(null);

        ProfissionalSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(profissional);

        assertThat(dto.getId())
                .isNull();
    }

}
