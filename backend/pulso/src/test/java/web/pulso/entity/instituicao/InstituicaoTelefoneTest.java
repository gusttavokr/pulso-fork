package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Testes de telefone da entidade Instituicao")
class InstituicaoTelefoneTest {

    @Test
    @DisplayName("Deve lançar erro quando telefone for nulo")
    void deveLancarErroQuandoTelefoneForNulo() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setTelefone(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O telefone da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve lançar erro quando telefone estiver vazio")
    void deveLancarErroQuandoTelefoneEstiverVazio() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setTelefone("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O telefone da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar telefone com tamanho máximo válido")
    void deveAceitarTelefoneCom20Caracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setTelefone("1".repeat(20));

        assertDoesNotThrow(instituicao::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando telefone ultrapassar 20 caracteres")
    void deveLancarErroQuandoTelefoneUltrapassar20Caracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setTelefone("1".repeat(21));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O telefone da instituição deve possuir no máximo 20 caracteres.");
    }
}
