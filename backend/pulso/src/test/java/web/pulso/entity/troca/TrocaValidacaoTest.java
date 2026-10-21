package web.pulso.entity.troca;

import org.junit.jupiter.api.Test;

import web.pulso.entity.Troca;

import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Teste de validação da entidade Troca")
public class TrocaValidacaoTest {

    @Test
    @DisplayName("Deve aceitar troca válida")
    void deveAceitarTrocaValida() {

        Troca troca = criarTrocaValida();

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando profissional de origem não for informado")
    void deveLancarErroQuandoProfissionalOrigemForNulo() {

        Troca troca = criarTrocaValida();

        troca.setProfissionalOrigem(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O profissional de origem da troca é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar profissional de destino nulo")
    void deveAceitarProfissionalDestinoNulo() {

        Troca troca = criarTrocaValida();

        troca.setProfissionalDestino(null);

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando plantão de origem não for informado")
    void deveLancarErroQuandoPlantaoOrigemForNulo() {

        Troca troca = criarTrocaValida();

        troca.setPlantaoOrigem(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("O plantão de origem da troca é obrigatório.");
    }

    @Test
    @DisplayName("Deve aceitar plantão de destino nulo")
    void deveAceitarPlantaoDestinoNulo() {

        Troca troca = criarTrocaValida();

        troca.setPlantaoDestino(null);

        assertDoesNotThrow(troca::validar);
    }
}