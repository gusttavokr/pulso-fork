package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.TipoPix;

@DisplayName("Testes da chave PIX email da entidade Profissional")
public class ProfissionalPixEmailTest {

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email estiver vazia")
        void deveLancarErroQuandoPixEmailEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email possuir apenas espaços")
        void deveLancarErroQuandoPixEmailPossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("   ");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email não possuir arroba")
        void deveLancarErroQuandoPixEmailNaoPossuirArroba() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("email.com");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email não possuir domínio")
        void deveLancarErroQuandoPixEmailNaoPossuirDominio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("email@");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email possuir menos de 5 caracteres")
        void deveLancarErroQuandoPixEmailPossuirMenosDeCincoCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("a@b");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve aceitar chave PIX email com tamanho mínimo válido")
        void deveAceitarPixEmailComTamanhoMinimoValido() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("a@b.c");
                profissional.setTipoPix(TipoPix.EMAIL);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar chave PIX email com tamanho máximo válido")
        void deveAceitarPixEmailCom255Caracteres() {

                String email = "a".repeat(243) + "@b.com";

                Profissional profissional = criarProfissionalValido();

                profissional.setPix(email);
                profissional.setTipoPix(TipoPix.EMAIL);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email ultrapassar 255 caracteres")
        void deveLancarErroQuandoPixEmailUltrapassar255Caracteres() {

                String email = "a".repeat(244) + "@b.com";

                Profissional profissional = criarProfissionalValido();

                profissional.setPix(email);
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email deve possuir no máximo 255 caracteres.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email possuir múltiplos arrobas")
        void deveLancarErroQuandoPixEmailPossuirMultiplosArrobas() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("email@@gmail.com");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email possuir espaços")
        void deveLancarErroQuandoPixEmailPossuirEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("meu email@gmail.com");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email iniciar com ponto")
        void deveLancarErroQuandoPixEmailIniciarComPonto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix(".email@gmail.com");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX email terminar com ponto")
        void deveLancarErroQuandoPixEmailTerminarComPonto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("email@gmail.com.");
                profissional.setTipoPix(TipoPix.EMAIL);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX email possui formato inválido.");
        }

}
