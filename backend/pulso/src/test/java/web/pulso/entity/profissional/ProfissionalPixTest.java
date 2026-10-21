package web.pulso.entity.profissional;

import org.junit.jupiter.api.Test;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.TipoPix;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Testes de PIX da entidade Profissional")
public class ProfissionalPixTest {

        @Test
        @DisplayName("Deve aceitar profissional sem chave PIX")
        void deveAceitarProfissionalSemPix() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix(null);
                profissional.setTipoPix(null);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando PIX for informado sem tipo")
        void deveLancarErroQuandoPixForInformadoSemTipo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("84999999999");
                profissional.setTipoPix(null);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O tipo da chave PIX deve ser informado.");
        }

        @Test
        @DisplayName("Deve lançar erro quando tipo PIX for informado sem chave")
        void deveLancarErroQuandoTipoPixForInformadoSemChave() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix(null);
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX deve ser informada.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX estiver vazia")
        void deveLancarErroQuandoPixEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX deve ser informada.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX possuir apenas espaços")
        void deveLancarErroQuandoPixPossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("   ");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX deve ser informada.");
        }

}
