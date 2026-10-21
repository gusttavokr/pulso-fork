package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;

@DisplayName("Testes de emails da entidade Instituicao")
class InstituicaoEmailsTest {

    @Test
    @DisplayName("Deve lançar erro quando lista de emails não for informada")
    void deveLancarErroQuandoListaEmailsForNula() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A instituição deve possuir pelo menos um email.");
    }

    @Test
    @DisplayName("Deve lançar erro quando lista de emails estiver vazia")
    void deveLancarErroQuandoListaEmailsEstiverVazia() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(List.of());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A instituição deve possuir pelo menos um email.");
    }

    @Test
    @DisplayName("Deve lançar erro quando email for nulo")
    void deveLancarErroQuandoEmailForNulo() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(Arrays.asList((String) null));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email da instituição não pode ser nulo.");
    }

    @Test
    @DisplayName("Deve lançar erro quando email estiver vazio")
    void deveLancarErroQuandoEmailEstiverVazio() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(List.of(""));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email da instituição é obrigatório.");
    }

    @Test
    @DisplayName("Deve lançar erro quando email possuir formato inválido")
    void deveLancarErroQuandoEmailPossuirFormatoInvalido() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(List.of("email.com"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email da instituição possui formato inválido.");
    }

    @Test
    @DisplayName("Deve aceitar email com 255 caracteres")
    void deveAceitarEmailCom255Caracteres() {

        String email = "a".repeat(249) + "@b.com";

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(List.of(email));

        assertDoesNotThrow(instituicao::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando email ultrapassar 255 caracteres")
    void deveLancarErroQuandoEmailUltrapassar255Caracteres() {

        String email = "a".repeat(250) + "@b.com";

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(List.of(email));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O email da instituição deve possuir no máximo 255 caracteres.");
    }

    @Test
    @DisplayName("Deve aceitar quantidade máxima de 5 emails")
    void deveAceitarQuantidadeMaximaDeCincoEmails() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(List.of(
                "email1@hospital.com",
                "email2@hospital.com",
                "email3@hospital.com",
                "email4@hospital.com",
                "email5@hospital.com"));

        assertDoesNotThrow(instituicao::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando quantidade de emails ultrapassar 5")
    void deveLancarErroQuandoQuantidadeEmailsUltrapassarCinco() {

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setEmails(List.of(
                "email1@hospital.com",
                "email2@hospital.com",
                "email3@hospital.com",
                "email4@hospital.com",
                "email5@hospital.com",
                "email6@hospital.com"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                instituicao::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A instituição pode possuir no máximo 5 emails.");
    }
}
