package web.pulso.mappers.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import web.pulso.dto.response.profissional.ProfissionalEstatisticaResponseDTO;
import web.pulso.entity.Profissional;
import web.pulso.service.mappers.ProfissionalMapper;

@DisplayName("Testes do método toEstatisticaResponseDTO da classe ProfissionalMapper")
public class ToEstatisticaResponseDTOTest {

    private ProfissionalMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new ProfissionalMapper(null, null, null, null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando profissional for nulo")
    void deveRetornarNullQuandoProfissionalForNulo() {

        ProfissionalEstatisticaResponseDTO dto = mapper.toEstatisticaResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Profissional profissional = criarProfissionalValido();

        profissional.setId(1L);

        ProfissionalEstatisticaResponseDTO dto = mapper.toEstatisticaResponseDTO(profissional);

        assertThat(dto.getId())
                .isEqualTo(profissional.getId());

        assertThat(dto.getNome())
                .isEqualTo(profissional.getNome());

        assertThat(dto.getFoto())
                .isEqualTo(profissional.getFotoPerfil());

        assertThat(dto.getTelefone())
                .isEqualTo(profissional.getTelefone());
    }

    @Test
    @DisplayName("Deve mapear emails corretamente")
    void deveMapearEmailsCorretamente() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalEstatisticaResponseDTO dto = mapper.toEstatisticaResponseDTO(profissional);

        assertThat(dto.getEmails())
                .containsExactlyElementsOf(
                        profissional.getEmails());
    }

    @Test
    @DisplayName("Deve criar lista vazia quando emails forem nulos")
    void deveCriarListaVaziaQuandoEmailsForemNulos() {

        Profissional profissional = criarProfissionalValido();

        profissional.setEmails(null);

        ProfissionalEstatisticaResponseDTO dto = mapper.toEstatisticaResponseDTO(profissional);

        assertThat(dto.getEmails())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de emails")
    void deveCriarNovaInstanciaDaListaDeEmails() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalEstatisticaResponseDTO dto = mapper.toEstatisticaResponseDTO(profissional);

        assertThat(dto.getEmails())
                .isNotSameAs(profissional.getEmails());

        assertThat(dto.getEmails())
                .containsExactlyElementsOf(
                        profissional.getEmails());
    }

    @Test
    @DisplayName("Deve criar nova instância de DTO")
    void deveCriarNovaInstanciaDeDto() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalEstatisticaResponseDTO dto = mapper.toEstatisticaResponseDTO(profissional);

        assertThat(dto)
                .isNotNull();

        assertThat(dto)
                .isNotSameAs(profissional);
    }

    @Test
    @DisplayName("Não deve alterar emails do profissional ao modificar emails do DTO")
    void naoDeveAlterarEmailsDoProfissionalAoModificarEmailsDoDto() {

        Profissional profissional = criarProfissionalValido();

        ProfissionalEstatisticaResponseDTO dto = mapper.toEstatisticaResponseDTO(profissional);

        dto.getEmails().add("novoemail@gmail.com");

        assertThat(profissional.getEmails())
                .doesNotContain("novoemail@gmail.com");

        assertThat(dto.getEmails())
                .contains("novoemail@gmail.com");
    }

}
