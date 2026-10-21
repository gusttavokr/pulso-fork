package web.pulso.mappers.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.response.instituicao.InstituicaoSimpleLoginResponseDTO;
import web.pulso.entity.Instituicao;
import web.pulso.service.mappers.InstituicaoMapper;

@DisplayName("Testes do método toSimpleLoginResponseDTO da classe InstituicaoMapper")
public class ToSimpleLoginResponseDTOTest {

    private InstituicaoMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new InstituicaoMapper(null, null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando instituição for nula")
    void deveRetornarNullQuandoInstituicaoForNula() {

        InstituicaoSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setId(1L);

        InstituicaoSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(instituicao);

        assertThat(dto.getId())
                .isEqualTo(instituicao.getId());

        assertThat(dto.getNome())
                .isEqualTo(instituicao.getNome());

        assertThat(dto.getFotoPerfil())
                .isEqualTo(instituicao.getFotoPerfil());
    }

    @Test
    @DisplayName("Deve mapear foto de perfil nula")
    void deveMapearFotoDePerfilNula() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setFotoPerfil(null);

        InstituicaoSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(instituicao);

        assertThat(dto.getFotoPerfil())
                .isNull();
    }

    @Test
    @DisplayName("Deve criar nova instância de DTO")
    void deveCriarNovaInstanciaDeDto() {

        Instituicao instituicao = criarInstituicaoValida();

        InstituicaoSimpleLoginResponseDTO dto = mapper.toSimpleLoginResponseDTO(instituicao);

        assertThat(dto)
                .isNotNull()
                .isNotSameAs(instituicao);
    }

}