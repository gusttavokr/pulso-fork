package web.pulso.entity.troca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;

@DisplayName("Testes de status da entidade Troca")
public class TrocaStatusTest {

    @Test
    @DisplayName("Deve lançar erro quando status não for informado")
    void deveLancarErroQuandoStatusForNulo() {

        Troca troca = criarTrocaValida();

        troca.setStatus(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O status da troca é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar status pendente")
    void deveAceitarStatusPendente() {

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve aceitar status confirmado")
    void deveAceitarStatusConfirmado() {

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.CONFIRMADO);

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve aceitar status recusado")
    void deveAceitarStatusRecusado() {

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.RECUSADO);

        assertDoesNotThrow(troca::validar);
    }

}
