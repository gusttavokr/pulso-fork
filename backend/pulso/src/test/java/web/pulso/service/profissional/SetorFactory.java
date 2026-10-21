package web.pulso.service.profissional;

import java.util.ArrayList;
import java.util.List;

import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;

import static web.pulso.service.profissional.EscalaFactory.criarEscalaValida;

public class SetorFactory {

    public static Setor criarSetorValido() {

        Setor setor = new Setor();

        setor.setId(1L);

        setor.setNome("UTI Adulto");

        setor.setInstituicao(
                InstituicaoFactory.criarInstituicaoValida());

        setor.setEspecialidade(
                Especialidade.CARDIOLOGIA);

        setor.setEscalas(new ArrayList<>());

        return setor;
    }

    public static Setor criarSetorSemInstituicao() {

        Setor setor = criarSetorValido();

        setor.setInstituicao(null);

        return setor;
    }

    public static Setor criarSetorSemEspecialidade() {

        Setor setor = criarSetorValido();

        setor.setEspecialidade(null);

        return setor;
    }

    public static Setor criarSetorSemNome() {

        Setor setor = criarSetorValido();

        setor.setNome(null);

        return setor;
    }

    public static Setor criarSetorComEscalas() {

        Setor setor = criarSetorValido();

        setor.setEscalas(
                List.of(criarEscalaValida()));

        return setor;
    }
}