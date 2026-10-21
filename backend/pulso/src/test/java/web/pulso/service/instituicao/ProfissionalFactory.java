package web.pulso.service.instituicao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;
import web.pulso.entity.enums.UF;

public class ProfissionalFactory {

    public static Profissional criarProfissionalValido() {

        Profissional profissional = new Profissional();

        profissional.setId(1L);

        profissional.setNome("João da Silva");

        profissional.setAnoFormacao(LocalDate.now().minusYears(5));

        profissional.setDataNasc(LocalDate.now().minusYears(30));

        profissional.setCrmNumero("12345");

        profissional.setCrms(Set.of(UF.RN));

        profissional.setEmails(List.of("email@gmail.com"));

        profissional.setTelefone("84999999999");

        profissional.setEspecialidades(Set.of(Especialidade.CARDIOLOGIA));

        profissional.setPix("12345678901");

        profissional.setTipoPix(TipoPix.CPF);

        profissional.setInstituicoesClt(new ArrayList<>());

        profissional.setInstituicoesPj(new ArrayList<>());

        profissional.setInstituicoesLiderEscala(new ArrayList<>());

        return profissional;
    }

    public static Profissional criarLiderComEspecialidadeCardiologia() {

        Profissional profissional = criarProfissionalValido();

        profissional.setInstituicoesLiderEscala(new ArrayList<>(
                List.of(InstituicaoFactory.criarInstituicaoValida())));

        profissional.setEspecialidades(Set.of(Especialidade.CARDIOLOGIA));

        return profissional;
    }
}
