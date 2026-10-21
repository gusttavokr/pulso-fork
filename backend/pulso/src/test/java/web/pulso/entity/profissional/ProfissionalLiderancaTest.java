package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

@DisplayName("Testes de liderança da entidade Profissional")
class ProfissionalLiderancaTest {

    @Test
    @DisplayName("Deve retornar true quando for líder da instituição")
    void deveRetornarTrueQuandoForLiderDaInstituicao() {

        Instituicao instituicao = new Instituicao();
        instituicao.setId(1L);

        Profissional profissional = criarProfissionalValido();

        // Setar o profissional como líder de escala da instituicao criada
        profissional.setInstituicoesLiderEscala(List.of(instituicao));

        boolean resultado = profissional.eLiderDaInstituicao(instituicao);

        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não for líder da instituição")
    void deveRetornarFalseQuandoNaoForLiderDaInstituicao() {

        Instituicao instituicao1 = new Instituicao();
        instituicao1.setId(1L);

        Instituicao instituicao2 = new Instituicao();
        instituicao2.setId(2L);

        Profissional profissional = criarProfissionalValido();

        profissional.setInstituicoesLiderEscala(List.of(instituicao1));

        boolean resultado = profissional.eLiderDaInstituicao(instituicao2);

        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando o profissional não possuir instituições de liderança")
    void deveRetornarFalseQuandoNaoPossuirInstituicoesDeLideranca() {

        Instituicao instituicao = new Instituicao();
        instituicao.setId(1L);

        Profissional profissional = criarProfissionalValido();

        boolean resultado = profissional.eLiderDaInstituicao(instituicao);

        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve retornar true quando instituições diferentes possuírem o mesmo ID")
    void deveRetornarTrueQuandoInstituicoesDiferentesPossuiremMesmoId() {

        Instituicao instituicaoLiderada = new Instituicao();
        instituicaoLiderada.setId(1L);

        Instituicao instituicaoBuscada = new Instituicao();
        instituicaoBuscada.setId(1L);

        Profissional profissional = criarProfissionalValido();

        profissional.setInstituicoesLiderEscala(List.of(instituicaoLiderada));

        boolean resultado = profissional.eLiderDaInstituicao(instituicaoBuscada);

        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Deve retornar true quando o profissional liderar uma entre várias instituições")
    void deveRetornarTrueQuandoLiderarUmaEntreVariasInstituicoes() {

        Instituicao instituicao1 = new Instituicao();
        instituicao1.setId(1L);

        Instituicao instituicao2 = new Instituicao();
        instituicao2.setId(2L);

        Instituicao instituicao3 = new Instituicao();
        instituicao3.setId(3L);

        Profissional profissional = criarProfissionalValido();

        profissional.setInstituicoesLiderEscala(
                List.of(instituicao1, instituicao2, instituicao3));

        boolean resultado = profissional.eLiderDaInstituicao(instituicao3);

        assertThat(resultado).isTrue();
    }
}