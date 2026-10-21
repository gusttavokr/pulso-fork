package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Testes de exigência de vínculo da entidade Instituicao")
class InstituicaoExigeVinculoTest {

    @Test
    @DisplayName("Deve lançar erro quando exigência de vínculo for nula")
    void deveLancarErroQuandoExigeVinculoForNulo() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setExigeVinculo(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A informação de exigência de vínculo da instituição é obrigatória.");
    }
}
