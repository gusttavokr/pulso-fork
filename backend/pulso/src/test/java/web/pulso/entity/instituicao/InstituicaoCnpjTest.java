package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Testes de CNPJ da entidade Instituicao")
class InstituicaoCnpjTest {

    @Test
    @DisplayName("Deve lançar erro quando CNPJ for nulo")
    void deveLancarErroQuandoCnpjForNulo() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setCnpj(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O CNPJ da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve lançar erro quando CNPJ estiver vazio")
    void deveLancarErroQuandoCnpjEstiverVazio() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setCnpj("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O CNPJ da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar CNPJ com 14 dígitos")
    void deveAceitarCnpjCom14Digitos() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setCnpj("12345678000199");

        assertDoesNotThrow(instituicao::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando CNPJ possuir menos de 14 dígitos")
    void deveLancarErroQuandoCnpjPossuirMenosDe14Digitos() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setCnpj("1234567800019");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O CNPJ da instituição deve conter exatamente 14 dígitos numéricos.");
    }

    @Test
    @DisplayName("Deve lançar erro quando CNPJ possuir letras")
    void deveLancarErroQuandoCnpjPossuirLetras() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setCnpj("1234567800019A");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O CNPJ da instituição deve conter exatamente 14 dígitos numéricos.");
    }
}
