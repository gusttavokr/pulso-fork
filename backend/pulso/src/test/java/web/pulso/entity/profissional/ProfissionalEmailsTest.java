package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;

@DisplayName("Testes de emails da entidade Profissional")
public class ProfissionalEmailsTest {

        @Test
        @DisplayName("Deve lançar erro quando lista de emails não for informada")
        void deveLancarErroQuandoListaEmailsForNula() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(null);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O profissional deve possuir pelo menos um email.");
        }

        @Test
        @DisplayName("Deve lançar erro quando lista de emails estiver vazia")
        void deveLancarErroQuandoListaEmailsEstiverVazia() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of());

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O profissional deve possuir pelo menos um email.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email for nulo")
        void deveLancarErroQuandoEmailForNulo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of((String) null));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional não pode ser nulo.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email estiver vazio")
        void deveLancarErroQuandoEmailEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of(""));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional é obrigatório.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email possuir apenas espaços")
        void deveLancarErroQuandoEmailPossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("   "));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional é obrigatório.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email não possuir arroba")
        void deveLancarErroQuandoEmailNaoPossuirArroba() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("email.com"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email não possuir domínio")
        void deveLancarErroQuandoEmailNaoPossuirDominio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("email@"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email possuir menos de 5 caracteres")
        void deveLancarErroQuandoEmailPossuirMenosDeCincoCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("a@b"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui tamanho inválido.");
        }

        // Por incrivel que pareca, isso é um email válido pela RFC de emails
        @Test
        @DisplayName("Deve aceitar email com tamanho mínimo válido")
        void deveAceitarMenorFormatoEmailValido() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("a@b.c"));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar email com tamanho máximo válido")
        void deveAceitarEmailCom255Caracteres() {

                String email = "a".repeat(243) + "@b.com";

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of(email));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando email ultrapassar 255 caracteres")
        void deveLancarErroQuandoEmailUltrapassar255Caracteres() {

                String email = "a".repeat(244) + "@b.com";

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of(email));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional deve possuir no máximo 255 caracteres.");
        }

        @Test
        @DisplayName("Deve aceitar múltiplos emails válidos")
        void deveAceitarMultiplosEmailsValidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of(
                                "email1@gmail.com",
                                "email2@gmail.com",
                                "email3@gmail.com"));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar quantidade máxima de 5 emails")
        void deveAceitarQuantidadeMaximaDeCincoEmails() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of(
                                "email1@gmail.com",
                                "email2@gmail.com",
                                "email3@gmail.com",
                                "email4@gmail.com",
                                "email5@gmail.com"));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando quantidade de emails ultrapassar 5")
        void deveLancarErroQuandoQuantidadeEmailsUltrapassarCinco() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of(
                                "email1@gmail.com",
                                "email2@gmail.com",
                                "email3@gmail.com",
                                "email4@gmail.com",
                                "email5@gmail.com",
                                "email6@gmail.com"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O profissional pode possuir no máximo 5 emails.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email possuir múltiplos arrobas")
        void deveLancarErroQuandoEmailPossuirMultiplosArrobas() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("email@@gmail.com"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email possuir espaços")
        void deveLancarErroQuandoEmailPossuirEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("meu email@gmail.com"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email iniciar com ponto")
        void deveLancarErroQuandoEmailIniciarComPonto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of(".email@gmail.com"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email terminar com ponto")
        void deveLancarErroQuandoEmailTerminarComPonto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("email@gmail.com."));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email possuir domínio sem ponto")
        void deveLancarErroQuandoEmailPossuirDominioSemPonto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("email@gmailcom"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email possuir domínio iniciado por ponto")
        void deveLancarErroQuandoEmailPossuirDominioIniciadoPorPonto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("email@.com"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando email possuir parte local vazia")
        void deveLancarErroQuandoEmailPossuirParteLocalVazia() {

                Profissional profissional = criarProfissionalValido();

                profissional.setEmails(List.of("@gmail.com"));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O email do profissional possui formato inválido.");
        }

}
