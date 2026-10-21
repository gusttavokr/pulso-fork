package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.TipoPix;

@DisplayName("Testes da chave PIX CPF da entidade Profissional")
public class ProfissionalPixCPFTest {

        @Test
        @DisplayName("Deve aceitar chave PIX CPF com exatamente 11 dígitos")
        void deveAceitarPixCpfComExatamenteOnzeDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("52998224725");
                profissional.setTipoPix(TipoPix.CPF);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF possuir menos de 11 dígitos")
        void deveLancarErroQuandoPixCpfPossuirMenosDeOnzeDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("1234567890");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF deve possuir 11 dígitos.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF ultrapassar 11 dígitos")
        void deveLancarErroQuandoPixCpfUltrapassarOnzeDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("123456789012");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF deve possuir 11 dígitos.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF possuir letras")
        void deveLancarErroQuandoPixCpfPossuirLetras() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("12345abc901");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF deve possuir apenas números.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF possuir caracteres especiais")
        void deveLancarErroQuandoPixCpfPossuirCaracteresEspeciais() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("12345@78901");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF deve possuir apenas números.");
        }

        @Test
        @DisplayName("Deve aceitar chave PIX CPF formatada com máscara")
        void deveAceitarPixCpfFormatadoComMascara() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("529.982.247-25");
                profissional.setTipoPix(TipoPix.CPF);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF possuir máscara inválida")
        void deveLancarErroQuandoPixCpfPossuirMascaraInvalida() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("529..982.-24725");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF estiver vazia")
        void deveLancarErroQuandoPixCpfEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF possuir apenas espaços")
        void deveLancarErroQuandoPixCpfPossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("   ");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF for inválida pela regra de validação")
        void deveLancarErroQuandoPixCpfForInvalido() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("11111111111");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF é inválida.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX CPF possuir dígitos verificadores inválidos")
        void deveLancarErroQuandoPixCpfPossuirDigitosVerificadoresInvalidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("52998224724");
                profissional.setTipoPix(TipoPix.CPF);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX CPF é inválida.");
        }

}
