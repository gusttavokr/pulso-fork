package web.pulso.entity.setor;

import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;

public class SetorFactory {
    public static Setor criarSetorValido(){

        Setor setor = new Setor();

        setor.setNome("Pediátrico");
        setor.setEspecialidade(Especialidade.PEDIATRIA);

        return setor;
    }
}
