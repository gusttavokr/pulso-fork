package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;

@DisplayName("Testes de telefone da entidade Profissional")
public class ProfissionalTelefoneTest {

        @Test
        @DisplayName("Deve aceitar telefone nulo")
        void deveAceitarTelefoneNulo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone(null);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone estiver vazio")
        void deveLancarErroQuandoTelefoneEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional não pode estar vazio.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir apenas espaços")
        void deveLancarErroQuandoTelefonePossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("   ");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional não pode estar vazio.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir letras")
        void deveLancarErroQuandoTelefonePossuirLetras() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("84999ABCD99");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir caracteres especiais inválidos")
        void deveLancarErroQuandoTelefonePossuirCaracteresInvalidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("84@99999#9999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir menos de 10 dígitos")
        void deveLancarErroQuandoTelefonePossuirMenosDeDezDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("849999999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve aceitar telefone com tamanho mínimo válido")
        void deveAceitarTelefoneComDezDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("8433334444");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar telefone celular com 11 dígitos")
        void deveAceitarTelefoneCelularComOnzeDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("84999999999");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar telefone formatado corretamente")
        void deveAceitarTelefoneFormatadoCorretamente() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("(84) 99999-9999");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar telefone com código internacional")
        void deveAceitarTelefoneComCodigoInternacional() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("+55 84 99999-9999");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone ultrapassar tamanho máximo permitido")
        void deveLancarErroQuandoTelefoneUltrapassarTamanhoMaximo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("+55 84 99999-99999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir múltiplos sinais de mais")
        void deveLancarErroQuandoTelefonePossuirMultiplosSinaisDeMais() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("++55 84 99999-9999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir parênteses inválidos")
        void deveLancarErroQuandoTelefonePossuirParentesesInvalidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("(84 99999-9999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir hífen em posição inválida")
        void deveLancarErroQuandoTelefonePossuirHifenEmPosicaoInvalida() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("84-99999-9999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir código internacional incompleto")
        void deveLancarErroQuandoTelefonePossuirCodigoInternacionalIncompleto() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("+55");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir DDD inválido")
        void deveLancarErroQuandoTelefonePossuirDddInvalido() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("(00) 99999-9999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui DDD inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir apenas símbolos")
        void deveLancarErroQuandoTelefonePossuirApenasSimbolos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("()-+");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve aceitar telefone com espaços válidos")
        void deveAceitarTelefoneComEspacosValidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("84 99999 9999");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir espaço no meio do DDD")
        void deveLancarErroQuandoTelefonePossuirEspacoNoMeioDoDdd() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("(8 4) 99999-9999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone possuir menos de 10 dígitos numéricos mesmo formatado")
        void deveLancarErroQuandoTelefoneFormatadoPossuirMenosDeDezDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("(84) 9999-999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando telefone formatado ultrapassar quantidade máxima de dígitos")
        void deveLancarErroQuandoTelefoneFormatadoUltrapassarQuantidadeMaximaDeDigitos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setTelefone("(84) 99999-99999");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O telefone do profissional possui tamanho inválido.");
        }

}
