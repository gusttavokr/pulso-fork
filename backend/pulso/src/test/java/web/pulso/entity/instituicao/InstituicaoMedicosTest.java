package web.pulso.entity.instituicao;

import static org.assertj.core.api.Assertions.assertThat;
import static web.pulso.entity.instituicao.InstituicaoFactory.criarInstituicaoValida;
import static web.pulso.entity.profissional.ProfissionalFactory.criarOutroProfissionalValido;
import static web.pulso.entity.profissional.ProfissionalFactory.criarProfissionalValido;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;

@DisplayName("Testes de médicos da entidade Instituicao")
class InstituicaoMedicosTest {

    @Test
    @DisplayName("Deve retornar todos os médicos CLT e PJ sem duplicidade")
    void deveRetornarTodosMedicosSemDuplicidade() {

        Profissional profissionalClt = criarProfissionalValido();
        Profissional profissionalPj = criarOutroProfissionalValido();

        Instituicao instituicao = criarInstituicaoValida();

        instituicao.setProfissionais_clt(List.of(profissionalClt, profissionalPj));
        instituicao.setProfisionais_pj(List.of(profissionalClt));

        List<Profissional> resultado = instituicao.getTodosMedicos();

        assertThat(resultado)
                .containsExactly(profissionalClt, profissionalPj);
    }
}
