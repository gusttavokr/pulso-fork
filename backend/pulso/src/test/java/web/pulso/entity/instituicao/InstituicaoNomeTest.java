package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Testes de nome da entidade Instituicao")
class InstituicaoNomeTest {

    @Test
    @DisplayName("Deve lançar erro quando nome não for informado")
    void deveLancarErroQuandoNomeForNulo() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setNome(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O nome da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve lançar erro quando nome estiver vazio")
    void deveLancarErroQuandoNomeEstiverVazio() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setNome("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O nome da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho mínimo válido")
    void deveAceitarNomeComTresCaracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setNome("UPA");

        assertDoesNotThrow(instituicao::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando nome possuir menos de 3 caracteres")
    void deveLancarErroQuandoNomePossuirMenosDeTresCaracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setNome("HU");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O nome da instituição deve possuir pelo menos 3 caracteres.");
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho máximo válido")
    void deveAceitarNomeCom100Caracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setNome("A".repeat(100));

        assertDoesNotThrow(instituicao::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando nome ultrapassar 100 caracteres")
    void deveLancarErroQuandoNomeUltrapassar100Caracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setNome("A".repeat(101));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O nome da instituição deve possuir no máximo 100 caracteres.");
    }
}
