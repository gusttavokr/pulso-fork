package web.pulso.entity.troca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Troca;

@DisplayName("Testes de datas da entidade Troca")
public class TrocaDatasTest {

    @Test
    @DisplayName("Deve lançar erro quando data de solicitação não for informada")
    void deveLancarErroQuandoDataSolicitacaoForNula() {

        Troca troca = criarTrocaValida();

        troca.setDataSolicitacao(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A data da solicitação da troca é obrigatória.");
    }

    @Test
    @DisplayName("Deve aceitar data de solicitação válida")
    void deveAceitarDataSolicitacaoValida() {

        Troca troca = criarTrocaValida();

        troca.setDataSolicitacao(LocalDateTime.now());

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve aceitar data de resposta nula")
    void deveAceitarDataRespostaNula() {

        Troca troca = criarTrocaValida();

        troca.setDataResposta(null);

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve aceitar data de resposta preenchida")
    void deveAceitarDataRespostaPreenchida() {

        Troca troca = criarTrocaValida();

        troca.setDataResposta(LocalDateTime.now());

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando data de resposta for anterior à data de solicitação")
    void deveLancarErroQuandoDataRespostaForAnteriorADataSolicitacao() {

        Troca troca = criarTrocaValida();

        troca.setDataSolicitacao(LocalDateTime.now());

        troca.setDataResposta(LocalDateTime.now().minusDays(1));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A data de resposta da troca não pode ser anterior à data de solicitação.");
    }

    @Test
    @DisplayName("Deve aceitar data de resposta igual à data de solicitação")
    void deveAceitarDataRespostaIgualADataSolicitacao() {

        Troca troca = criarTrocaValida();

        LocalDateTime data = LocalDateTime.now();

        troca.setDataSolicitacao(data);

        troca.setDataResposta(data);

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve aceitar data de resposta posterior à data de solicitação")
    void deveAceitarDataRespostaPosteriorADataSolicitacao() {

        Troca troca = criarTrocaValida();

        troca.setDataSolicitacao(LocalDateTime.now());

        troca.setDataResposta(LocalDateTime.now().plusDays(1));

        assertDoesNotThrow(troca::validar);
    }

    @Test
    @DisplayName("Deve lançar erro quando data de solicitação estiver no futuro")
    void deveLancarErroQuandoDataSolicitacaoEstiverNoFuturo() {

        Troca troca = criarTrocaValida();

        troca.setDataSolicitacao(LocalDateTime.now().plusDays(1));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A data da solicitação da troca não pode estar no futuro.");
    }

    @Test
    @DisplayName("Deve lançar erro quando data de resposta estiver no futuro")
    void deveLancarErroQuandoDataRespostaEstiverNoFuturo() {

        Troca troca = criarTrocaValida();

        troca.setDataResposta(LocalDateTime.now().plusDays(1));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                troca::validar);

        assertThat(exception.getMessage())
                .isEqualTo("A data de resposta da troca não pode estar no futuro.");
    }

}
