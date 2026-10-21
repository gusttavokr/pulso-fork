package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.TipoPix;

@DisplayName("Testes da chave PIX telefone da entidade Profissional")
public class ProfissionalPixTelefoneTest {

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone estiver vazia")
        void deveLancarErroQuandoPixTelefoneEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone possuir apenas espaços")
        void deveLancarErroQuandoPixTelefonePossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("   ");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone possuir letras")
        void deveLancarErroQuandoPixTelefonePossuirLetras() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("84999ABCD99");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone possuir caracteres especiais inválidos")
        void deveLancarErroQuandoPixTelefonePossuirCaracteresEspeciaisInvalidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("84@99999#9999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone possui formato inválido.");
        }

        @Test
        @DisplayName("Deve aceitar chave PIX telefone fixo com 10 dígitos")
        void deveAceitarPixTelefoneComDezDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("8433334444");
                profissional.setTipoPix(TipoPix.TELEFONE);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar chave PIX telefone celular com 11 dígitos")
        void deveAceitarPixTelefoneCelularComOnzeDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("84999999999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar chave PIX telefone formatada corretamente")
        void deveAceitarPixTelefoneFormatadoCorretamente() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("(84) 99999-9999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar chave PIX telefone com código internacional")
        void deveAceitarPixTelefoneComCodigoInternacional() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("+55 84 99999-9999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone possuir menos de 10 dígitos")
        void deveLancarErroQuandoPixTelefonePossuirMenosDeDezDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("849999999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone ultrapassar tamanho máximo permitido")
        void deveLancarErroQuandoPixTelefoneUltrapassarTamanhoMaximo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("+55 84 99999-99999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone possuir múltiplos sinais de mais")
        void deveLancarErroQuandoPixTelefonePossuirMultiplosSinaisDeMais() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("++55 84 99999-9999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone possuir parênteses inválidos")
        void deveLancarErroQuandoPixTelefonePossuirParentesesInvalidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("(84 99999-9999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX telefone possuir hífen em posição inválida")
        void deveLancarErroQuandoPixTelefonePossuirHifenEmPosicaoInvalida() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("84-99999-9999");
                profissional.setTipoPix(TipoPix.TELEFONE);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX telefone possui formato inválido.");
        }

}