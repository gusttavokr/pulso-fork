package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.UF;

@DisplayName("Testes de CRM da entidade Profissional")
public class ProfissionalCRMTest {

        @Test
        @DisplayName("Deve lançar erro quando CRM não for informado")
        void deveLancarErroQuandoCrmForNulo() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero(null);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional é obrigatório.");
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM estiver vazio")
        void deveLancarErroQuandoCrmEstiverVazio() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional é obrigatório.");
        }

        @Test
        @DisplayName("Deve aceitar CRM com tamanho mínimo válido")
        void deveAceitarCrmComQuatroCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("1234");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM possuir menos de 4 caracteres")
        void deveLancarErroQuandoCrmPossuirMenosDeQuatroCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("123");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir pelo menos 4 caracteres.");
        }

        @Test
        @DisplayName("Deve aceitar CRM com tamanho máximo válido")
        void deveAceitarCrmComDezCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("1234567890");

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM ultrapassar 10 caracteres")
        void deveLancarErroQuandoCrmUltrapassarDezCaracteres() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12345678901");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir no máximo 10 caracteres.");
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM possuir letras")
        void deveLancarErroQuandoCrmPossuirLetras() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12AB34");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir apenas números.");
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM possuir caracteres especiais")
        void deveLancarErroQuandoCrmPossuirCaracteresEspeciais() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("1234-5");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir apenas números.");
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM possuir espaços")
        void deveLancarErroQuandoCrmPossuirEspacos() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12 345");

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir apenas números.");
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM não possuir UF cadastrada")
        void deveLancarErroQuandoCrmNaoPossuirUf() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12345");
                profissional.setCrms(Set.of());

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir pelo menos uma UF.");
        }

        @Test
        @DisplayName("Deve lançar erro quando coleção de UF do CRM for nula")
        void deveLancarErroQuandoColecaoUfCrmForNula() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12345");
                profissional.setCrms(null);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir pelo menos uma UF.");
        }

        @Test
        @DisplayName("Deve lançar erro quando coleção de UF do CRM estiver vazia")
        void deveLancarErroQuandoColecaoUfCrmEstiverVazia() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12345");
                profissional.setCrms(Set.of());

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional deve possuir pelo menos uma UF.");
        }

        @Test
        @DisplayName("Deve aceitar CRM com uma UF válida")
        void deveAceitarCrmComUmaUf() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12345");
                profissional.setCrms(Set.of(UF.RN));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve aceitar CRM com múltiplas UF")
        void deveAceitarCrmComMultiplasUf() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12345");

                profissional.setCrms(Set.of(
                                UF.RN,
                                UF.PB,
                                UF.CE));

                assertDoesNotThrow(profissional::validar);
        }

        @Test
        @DisplayName("Deve lançar erro quando CRM possuir UF nula")
        void deveLancarErroQuandoCrmPossuirUfNula() {

                Profissional profissional = criarProfissionalValido();

                profissional.setCrmNumero("12345");

                Set<UF> ufs = new HashSet<>();
                ufs.add(UF.RN);
                ufs.add(null);

                profissional.setCrms(ufs);

                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                profissional::validar);

                assertThat(exception.getMessage())
                                .isEqualTo("O CRM do profissional não pode possuir UF nula.");
        }

}
