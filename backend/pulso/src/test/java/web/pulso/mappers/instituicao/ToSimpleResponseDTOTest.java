package web.pulso.mappers.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.service.mappers.InstituicaoMapper;

@DisplayName("Testes do método toSimpleResponseDTO da classe InstituicaoMapper")
public class ToSimpleResponseDTOTest {

    private InstituicaoMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new InstituicaoMapper(null, null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando instituição for nula")
    void deveRetornarNullQuandoInstituicaoForNula() {

        InstituicaoSimpleResponseDTO dto = mapper.toSimpleResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setId(1L);

        InstituicaoSimpleResponseDTO dto = mapper.toSimpleResponseDTO(instituicao);

        assertThat(dto.getId())
                .isEqualTo(instituicao.getId());

        assertThat(dto.getNome())
                .isEqualTo(instituicao.getNome());

        assertThat(dto.getEndereco())
                .isEqualTo(instituicao.getEndereco());

        assertThat(dto.getCnpj())
                .isEqualTo(instituicao.getCnpj());
    }

    @Test
    @DisplayName("Deve mapear emails corretamente")
    void deveMapearEmailsCorretamente() {

        Instituicao instituicao = criarInstituicaoValida();

        InstituicaoSimpleResponseDTO dto = mapper.toSimpleResponseDTO(instituicao);

        assertThat(dto.getEmails())
                .containsExactlyElementsOf(instituicao.getEmails());
    }

    @Test
    @DisplayName("Deve criar lista vazia quando emails forem nulos")
    void deveCriarListaVaziaQuandoEmailsForemNulos() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(null);

        InstituicaoSimpleResponseDTO dto = mapper.toSimpleResponseDTO(instituicao);

        assertThat(dto.getEmails())
                .isNotNull()
                .isEmpty();
    }

}