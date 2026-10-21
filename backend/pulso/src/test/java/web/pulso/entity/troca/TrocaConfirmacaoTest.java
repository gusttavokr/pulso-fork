package web.pulso.entity.troca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static web.pulso.entity.profissional.ProfissionalFactory.criarOutroProfissionalValido;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;
import static web.pulso.entity.troca.TrocaFactory.criarTrocaValida;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;

@DisplayName("Testes de confirmação da entidade Troca")
public class TrocaConfirmacaoTest {

    @Test
    @DisplayName("Deve lançar erro ao confirmar troca com profissional nulo")
    void deveLancarErroAoConfirmarTrocaComProfissionalNulo() {

        Troca troca = criarTrocaValida();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> troca.confirmarParaProfissional(null));

        assertThat(exception.getMessage())
                .isEqualTo("Profissional não pode ser nulo");
    }

    @Test
    @DisplayName("Deve definir profissional destino ao confirmar troca")
    void deveDefinirProfissionalDestinoAoConfirmarTroca() {

        Troca troca = criarTrocaValida();

        Profissional profissional = criarProfissionalValido();

        troca.confirmarParaProfissional(profissional);

        assertThat(troca.getProfissionalDestino())
                .isEqualTo(profissional);
    }

    @Test
    @DisplayName("Deve alterar status para confirmado ao confirmar troca")
    void deveAlterarStatusParaConfirmadoAoConfirmarTroca() {

        Troca troca = criarTrocaValida();

        Profissional profissional = criarProfissionalValido();

        troca.confirmarParaProfissional(profissional);

        assertThat(troca.getStatus())
                .isEqualTo(StatusTroca.CONFIRMADO);
    }

    @Test
    @DisplayName("Não deve alterar profissional destino quando troca já estiver confirmada")
    void naoDeveAlterarProfissionalDestinoQuandoTrocaJaEstiverConfirmada() {

        Troca troca = criarTrocaValida();

        Profissional profissionalOriginal = criarProfissionalValido();

        Profissional novoProfissional = criarOutroProfissionalValido();

        troca.confirmarParaProfissional(profissionalOriginal);

        troca.confirmarParaProfissional(novoProfissional);

        assertThat(troca.getProfissionalDestino())
                .isEqualTo(profissionalOriginal);
    }

    @Test
    @DisplayName("Não deve alterar status quando troca já estiver confirmada")
    void naoDeveAlterarStatusQuandoTrocaJaEstiverConfirmada() {

        Troca troca = criarTrocaValida();

        Profissional profissional = criarProfissionalValido();

        troca.confirmarParaProfissional(profissional);

        troca.confirmarParaProfissional(criarOutroProfissionalValido());

        assertThat(troca.getStatus())
                .isEqualTo(StatusTroca.CONFIRMADO);
    }

}