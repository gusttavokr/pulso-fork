package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Testes de endereço da entidade Instituicao")
class InstituicaoEnderecoTest {

    @Test
    @DisplayName("Deve lançar erro quando endereço for nulo")
    void deveLancarErroQuandoEnderecoForNulo() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEndereco(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O endereço da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve lançar erro quando endereço estiver vazio")
    void deveLancarErroQuandoEnderecoEstiverVazio() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEndereco("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O endereço da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar endereço com 255 caracteres")
    void deveAceitarEnderecoCom255Caracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEndereco("A".repeat(255));

        assertDoesNotThrow(instituicao::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando endereço ultrapassar 255 caracteres")
    void deveLancarErroQuandoEnderecoUltrapassar255Caracteres() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEndereco("A".repeat(256));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O endereço da instituição deve possuir no máximo 255 caracteres.");
    }
}
