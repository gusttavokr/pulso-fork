package web.pulso.entity.profissional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import web.pulso.entity.Profissional;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;
import web.pulso.entity.enums.UF;

public class ProfissionalFactory {

    public static Profissional criarProfissionalValido() {

        Profissional profissional = new Profissional();

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

        return profissional;
    }

    public static Profissional criarOutroProfissionalValido() {

        Profissional profissional = new Profissional();

        profissional.setNome("Maria Oliveira");

        profissional.setAnoFormacao(LocalDate.now().minusYears(8));

        profissional.setDataNasc(LocalDate.now().minusYears(35));

        profissional.setCrmNumero("54321");

        profissional.setCrms(Set.of(UF.PB));

        profissional.setEmails(List.of("maria@gmail.com"));

        profissional.setTelefone("84988888888");

        profissional.setEspecialidades(Set.of(Especialidade.NEUROLOGIA));

        profissional.setPix("10987654321");

        profissional.setTipoPix(TipoPix.CPF);

        return profissional;
    }
}