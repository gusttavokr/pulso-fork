package web.pulso.mappers.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.mappers.instituicao.InstituicaoRequestDTOFactory.criarInstituicaoRequestDTOValido;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.entity.Instituicao;
import web.pulso.service.mappers.InstituicaoMapper;

@DisplayName("Testes do método toEntity da classe InstituicaoMapper")
public class ToEntityTest {

    private InstituicaoMapper mapper;

    @BeforeEach
    void setUp() {

        mapper = new InstituicaoMapper(null, null, null);
    }

    @Test
    @DisplayName("Deve retornar null quando dto for nulo")
    void deveRetornarNullQuandoDtoForNulo() {

        Instituicao instituicao = mapper.toEntity(null);

        assertThat(instituicao).isNull();
    }

    @Test
    @DisplayName("Deve mapear campos simples corretamente")
    void deveMapearCamposSimplesCorretamente() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Instituicao instituicao = mapper.toEntity(dto);

        assertThat(instituicao.getNome())
                .isEqualTo(dto.getNome());

        assertThat(instituicao.getTelefone())
                .isEqualTo(dto.getTelefone());

        assertThat(instituicao.getFotoPerfil())
                .isEqualTo(dto.getFotoPerfil());

        assertThat(instituicao.getEndereco())
                .isEqualTo(dto.getEndereco());

        assertThat(instituicao.getCnpj())
                .isEqualTo(dto.getCnpj());

        assertThat(instituicao.getExigeVinculo())
                .isEqualTo(dto.getExigeVinculo());
    }

    @Test
    @DisplayName("Deve mapear emails corretamente")
    void deveMapearEmailsCorretamente() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        dto.setEmails(List.of(
                "email1@gmail.com",
                "email2@gmail.com"));

        Instituicao instituicao = mapper.toEntity(dto);

        assertThat(instituicao.getEmails())
                .containsExactlyInAnyOrder(
                        "email1@gmail.com",
                        "email2@gmail.com");
    }

    @Test
    @DisplayName("Deve criar lista vazia quando emails forem nulos")
    void deveCriarListaVaziaQuandoEmailsForemNulos() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        dto.setEmails(null);

        Instituicao instituicao = mapper.toEntity(dto);

        assertThat(instituicao.getEmails())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Deve criar nova instância da lista de emails")
    void deveCriarNovaInstanciaDaListaDeEmails() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Instituicao instituicao = mapper.toEntity(dto);

        assertThat(instituicao.getEmails())
                .isNotSameAs(dto.getEmails());

        assertThat(instituicao.getEmails())
                .containsExactlyElementsOf(dto.getEmails());
    }

    @Test
    @DisplayName("Deve criar nova instância de instituição")
    void deveCriarNovaInstanciaDeInstituicao() {

        InstituicaoRequestDTO dto = criarInstituicaoRequestDTOValido();

        Instituicao instituicao = mapper.toEntity(dto);

        assertThat(instituicao)
                .isNotNull()
                .isNotSameAs(dto);
    }

}