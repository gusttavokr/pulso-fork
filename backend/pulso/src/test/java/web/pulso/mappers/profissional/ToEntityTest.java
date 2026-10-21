package web.pulso.mappers.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.mappers.profissional.ProfissionalRequestDTOFactory.criarProfissionalRequestDTOValido;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.entity.Profissional;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.UF;
import web.pulso.service.mappers.ProfissionalMapper;

@DisplayName("Testes do método toEntity da classe ProfissionalMapper")
public class ToEntityTest {

    private ProfissionalMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new ProfissionalMapper(null, null, null, null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando dto for nulo")
    void deveRetornarNullQuandoDtoForNulo() {

        Profissional profissional = mapper.toEntity(null);

        assertThat(profissional).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getNome())
                .isEqualTo(dto.getNome());

        assertThat(profissional.getTelefone())
                .isEqualTo(dto.getTelefone());

        assertThat(profissional.getFotoPerfil())
                .isEqualTo(dto.getFotoPerfil());

        assertThat(profissional.getAnoFormacao())
                .isEqualTo(dto.getAnoFormacao());

        assertThat(profissional.getPix())
                .isEqualTo(dto.getPix());

        assertThat(profissional.getTipoPix())
                .isEqualTo(dto.getTipoPix());

        assertThat(profissional.getCrmNumero())
                .isEqualTo(dto.getCrmNumero());

        assertThat(profissional.getDataNasc())
                .isEqualTo(dto.getDataNasc());
    }

    @Test
    @DisplayName("Deve mapear emails corretamente")
    void deveMapearEmailsCorretamente() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        dto.setEmails(List.of(
                "email1@gmail.com",
                "email2@gmail.com"));

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getEmails())
                .containsExactlyInAnyOrder(
                        "email1@gmail.com",
                        "email2@gmail.com");
    }

    @Test
    @DisplayName("Deve mapear CRMs corretamente")
    void deveMapearCrmsCorretamente() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        dto.setCrms(Set.of(UF.RN, UF.PB));

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getCrms())
                .containsExactlyInAnyOrder(
                        UF.RN,
                        UF.PB);
    }

    @Test
    @DisplayName("Deve mapear especialidades corretamente")
    void deveMapearEspecialidadesCorretamente() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        dto.setEspecialidades(Set.of(
                Especialidade.CARDIOLOGIA,
                Especialidade.NEUROLOGIA));

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getEspecialidades())
                .containsExactlyInAnyOrder(
                        Especialidade.CARDIOLOGIA,
                        Especialidade.NEUROLOGIA);
    }

    @Test
    @DisplayName("Deve criar lista vazia quando emails forem nulos")
    void deveCriarListaVaziaQuandoEmailsForemNulos() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        dto.setEmails(null);

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getEmails())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar conjunto vazio quando CRMs forem nulos")
    void deveCriarConjuntoVazioQuandoCrmsForemNulos() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        dto.setCrms(null);

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getCrms())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar conjunto vazio quando especialidades forem nulas")
    void deveCriarConjuntoVazioQuandoEspecialidadesForemNulas() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        dto.setEspecialidades(null);

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getEspecialidades())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de emails")
    void deveCriarNovaInstanciaDaListaDeEmails() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getEmails())
                .isNotSameAs(dto.getEmails());

        assertThat(profissional.getEmails())
                .containsExactlyElementsOf(dto.getEmails());
    }

    @Test
    @DisplayName("Deve criar nova instância do conjunto de CRMs")
    void deveCriarNovaInstanciaDoConjuntoDeCrms() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getCrms())
                .isNotSameAs(dto.getCrms());

        assertThat(profissional.getCrms())
                .containsExactlyInAnyOrderElementsOf(dto.getCrms());
    }

    @Test
    @DisplayName("Deve criar nova instância do conjunto de especialidades")
    void deveCriarNovaInstanciaDoConjuntoDeEspecialidades() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional.getEspecialidades())
                .isNotSameAs(dto.getEspecialidades());

        assertThat(profissional.getEspecialidades())
                .containsExactlyInAnyOrderElementsOf(dto.getEspecialidades());
    }

    @Test
    @DisplayName("Deve criar nova instância de profissional")
    void deveCriarNovaInstanciaDeProfissional() {

        ProfissionalRequestDTO dto = criarProfissionalRequestDTOValido();

        Profissional profissional = mapper.toEntity(dto);

        assertThat(profissional)
                .isNotNull()
                .isNotSameAs(dto);
    }

}
