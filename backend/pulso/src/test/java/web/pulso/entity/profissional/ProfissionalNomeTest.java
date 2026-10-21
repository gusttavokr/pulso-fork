package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;

@DisplayName("Testes de nome da entidade Profissional")
public class ProfissionalNomeTest {

        @Test
        @DisplayName("Deve lançar erro quando nome não for informado")
        void deveLancarErroQuandoNomeForNulo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome(null);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O nome do profissional é obrigatório.");
        }

        @Test
        @DisplayName("Deve lançar erro quando nome estiver vazio")
        void deveLancarErroQuandoNomeEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome("");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O nome do profissional é obrigatório.");
        }

        @Test
        @DisplayName("Deve lançar erro quando nome possuir apenas espaços")
        void deveLancarErroQuandoNomePossuirApenasEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome("   ");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O nome do profissional é obrigatório.");
        }

        @Test
        @DisplayName("Deve aceitar nome com tamanho mínimo válido")
        void deveAceitarNomeComTresCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome("Ana");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando nome possuir menos de 3 caracteres")
        void deveLancarErroQuandoNomePossuirMenosDeTresCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome("Al");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O nome do profissional deve possuir pelo menos 3 caracteres.");
        }

        @Test
        @DisplayName("Deve aceitar nome com tamanho máximo válido")
        void deveAceitarNomeCom255Caracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome("A".repeat(255));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando nome ultrapassar 255 caracteres")
        void deveLancarErroQuandoNomeUltrapassar255Caracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setNome("A".repeat(256));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O nome do profissional deve possuir no máximo 255 caracteres.");
        }

}
