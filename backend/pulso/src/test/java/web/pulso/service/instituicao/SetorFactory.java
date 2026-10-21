package web.pulso.service.instituicao;

import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;

public class SetorFactory {

    public static Setor criarSetorCardiologia() {

        Setor setor = new Setor();

        setor.setId(1L);

        setor.setNome("UTI Adulto");

        setor.setEspecialidade(Especialidade.CARDIOLOGIA);

        return setor;
    }

    public static Setor criarSetorNeurologia() {

        Setor setor = new Setor();

        setor.setId(2L);

        setor.setNome("Neurologia");

        setor.setEspecialidade(Especialidade.NEUROLOGIA);

        return setor;
    }
}
