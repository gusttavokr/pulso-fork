package web.pulso.entity.troca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Troca;
import web.pulso.entity.enums.TipoTroca;

@DisplayName("Testes de tipo da entidade Troca")
public class TrocaTipoTest {

    @Test
    @DisplayName("Deve lançar erro quando tipo não for informado")
    void deveLancarErroQuandoTipoForNulo() {

        Troca troca = criarTrocaValida();

        troca.setTipo(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O tipo da troca é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar troca do tipo simples")
    void deveAceitarTrocaTipoSimples() {

        Troca troca = criarTrocaValida();

        troca.setTipo(TipoTroca.SIMPLES);

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve aceitar troca do tipo dupla")
    void deveAceitarTrocaTipoDupla() {

        Troca troca = criarTrocaValida();

        troca.setTipo(TipoTroca.DUPLA);

        assertDoesNotThrow(troca::validar);
    }

}
