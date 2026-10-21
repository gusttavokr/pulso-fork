package web.pulso.entity.instituicao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Teste de validação da entidade Instituicao")
class InstituicaoValidacaoTest {

    @Test
    @DisplayName("Deve aceitar instituição válida")
    void deveAceitarInstituicaoValida() {

        Instituicao instituicao = criarInstituicaoValida();

        assertDoesNotThrow(instituicao::validar);
    }
}
