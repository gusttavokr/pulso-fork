package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.TipoPix;

@DisplayName("Testes de chave PIX aleatória da entidade Profissional")
public class ProfissionalPixAleatoriaTest {

        @Test
        @DisplayName("Deve aceitar chave PIX aleatória válida")
        void deveAceitarPixAleatorioValido() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550e8400-e29b-41d4-a716-446655440000");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar chave PIX aleatória válida com letras maiúsculas")
        void deveAceitarPixAleatorioComLetrasMaiusculas() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550E8400-E29B-41D4-A716-446655440000");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória estiver vazia")
        void deveLancarErroQuandoPixAleatorioEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória possuir apenas espaços")
        void deveLancarErroQuandoPixAleatorioPossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("   ");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória é obrigatória.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória possuir menos de 36 caracteres")
        void deveLancarErroQuandoPixAleatorioPossuirMenosDe36Caracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550e8400-e29b-41d4-a716");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória ultrapassar 36 caracteres")
        void deveLancarErroQuandoPixAleatorioUltrapassar36Caracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550e8400-e29b-41d4-a716-4466554400001");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória possui tamanho inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória não possuir hífens")
        void deveLancarErroQuandoPixAleatorioNaoPossuirHifens() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550e8400e29b41d4a716446655440000");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória possuir caracteres inválidos")
        void deveLancarErroQuandoPixAleatorioPossuirCaracteresInvalidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550e8400-e29b-41d4-a716-44665544ZZZZ");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória possuir hífens em posições inválidas")
        void deveLancarErroQuandoPixAleatorioPossuirHifensEmPosicoesInvalidas() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550e840-0e29b-41d4-a716-446655440000");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória possui formato inválido.");
        }

        @Test
        @DisplayName("Deve lançar erro quando chave PIX aleatória possuir caracteres especiais inválidos")
        void deveLancarErroQuandoPixAleatorioPossuirCaracteresEspeciaisInvalidos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setPix("550e8400-e29b-41d4-a716-44665544@000");
                profissional.setTipoPix(TipoPix.ALEATORIA);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A chave PIX aleatória possui formato inválido.");
        }

}
