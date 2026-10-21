package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Profissional;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

@DisplayName("Testes de datas da entidade Profissional")
public class ProfissionalDatasTest {

        @Test
        @DisplayName("Deve lançar erro quando o ano de formação for no futuro")
        void deveLancarErroQuandoAnoFormacaoForNoFuturo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setAnoFormacao(LocalDate.now().plusYears(1));
                profissional.setDataNasc(LocalDate.now().minusYears(30));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O ano de formação não pode ser no futuro.");
        }

        @Test
        @DisplayName("Deve lançar erro quando o profissional for menor de idade")
        void deveLancarErroQuandoProfissionalForMenorDeIdade() {

                Profissional profissional = criarProfissionalValido();

                profissional.setAnoFormacao(LocalDate.of(2020, 1, 1));
                profissional.setDataNasc(LocalDate.now().minusYears(17));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O profissional deve ter pelo menos 18 anos de idade.");
        }

        @Test
        @DisplayName("Deve aceitar profissional com exatamente 18 anos")
        void deveAceitarProfissionalComExatamente18Anos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setDataNasc(LocalDate.now().minusYears(18));

                profissional.setAnoFormacao(LocalDate.now());

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar ano de formação atual")
        void deveAceitarAnoFormacaoAtual() {

                Profissional profissional = criarProfissionalValido();

                profissional.setAnoFormacao(LocalDate.now());
                profissional.setDataNasc(LocalDate.now().minusYears(30));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando o ano de formação for anterior à data de nascimento")
        void deveLancarErroQuandoAnoFormacaoForAntesDoNascimento() {

                Profissional profissional = criarProfissionalValido();

                profissional.setDataNasc(LocalDate.of(2000, 1, 1));
                profissional.setAnoFormacao(LocalDate.of(1990, 1, 1));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O ano de formação não pode ser anterior à data de nascimento.");
        }

        @Test
        @DisplayName("Deve lançar erro quando o profissional se formar antes dos 18 anos")
        void deveLancarErroQuandoProfissionalSeFormaAntesDos18Anos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setDataNasc(LocalDate.of(2000, 1, 1));
                profissional.setAnoFormacao(LocalDate.of(2010, 1, 1));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O profissional não pode ter se formado antes dos 18 anos.");
        }

        @Test
        @DisplayName("Deve lançar erro quando o profissional possuir idade inválida")
        void deveLancarErroQuandoProfissionalTemIdadeInvalida() {

                Profissional profissional = criarProfissionalValido();

                profissional.setDataNasc(LocalDate.now().minusYears(150));
                profissional.setAnoFormacao(LocalDate.of(1950, 1, 1));

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("A idade do profissional é inválida.");
        }
}