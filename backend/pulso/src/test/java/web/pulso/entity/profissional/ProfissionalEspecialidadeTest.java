package web.pulso.entity.profissional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Testes de especialidade da entidade Profissional")
class ProfissionalEspecialidadeTest {

    @Test
    @DisplayName("Deve retornar true quando possuir a especialidade do setor")
    void deveRetornarTrueQuandoPossuirEspecialidadeDoSetor() {

        Setor setor = new Setor();
        setor.setEspecialidade(Especialidade.CARDIOLOGIA);

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(Set.of(Especialidade.CARDIOLOGIA));

        // esse método era pra retonar true se o setor fosse de uma instituicao que o
        // profissional está vinculado
        boolean resultado = profissional.possuiEspecialidadeParaSetor(setor);

        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não possuir a especialidade do setor")
    void deveRetornarFalseQuandoNaoPossuirEspecialidadeDoSetor() {

        Setor setor = new Setor();
        setor.setEspecialidade(Especialidade.CARDIOLOGIA);

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(Set.of(Especialidade.ORTOPEDIA));

        boolean resultado = profissional.possuiEspecialidadeParaSetor(setor);

        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve retornar true quando possuir especialidade entre várias cadastradas")
    void deveRetornarTrueQuandoPossuirEspecialidadeEntreVariasCadastradas() {

        Setor setor = new Setor();
        setor.setEspecialidade(Especialidade.PEDIATRIA);

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(Set.of(
                Especialidade.CARDIOLOGIA,
                Especialidade.ORTOPEDIA,
                Especialidade.PEDIATRIA));

        boolean resultado = profissional.possuiEspecialidadeParaSetor(setor);

        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não possuir especialidades cadastradas")
    void deveRetornarFalseQuandoNaoPossuirEspecialidadesCadastradas() {

        Setor setor = new Setor();
        setor.setEspecialidade(Especialidade.CARDIOLOGIA);

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(Set.of());

        boolean resultado = profissional.possuiEspecialidadeParaSetor(setor);

        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando setor não possuir especialidade definida")
    void deveRetornarFalseQuandoSetorNaoPossuirEspecialidadeDefinida() {

        Setor setor = new Setor();
        setor.setEspecialidade(null);

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(Set.of(Especialidade.CARDIOLOGIA));

        boolean resultado = profissional.possuiEspecialidadeParaSetor(setor);

        assertThat(resultado).isFalse();
    }

    @Test
    @DisplayName("Deve lançar erro quando coleçãod e especialidades não estiver inicializada")
    void deveLancarErroQuandoColecaoDeEspecialidadesNaoEstiverInicializada() {

        Setor setor = new Setor();
        setor.setEspecialidade(Especialidade.CARDIOLOGIA);

        Profissional profissional = criarProfissionalValido();

        profissional.setEspecialidades(null);

        assertThatThrownBy(() -> profissional.possuiEspecialidadeParaSetor(setor))
                .isInstanceOf(NullPointerException.class);
    }
}
