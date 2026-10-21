package web.pulso.entity.troca;

import java.time.LocalDateTime;

import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;
import web.pulso.entity.profissional.ProfissionalFactory;
import web.pulso.service.profissional.PlantaoFactory;

public class TrocaFactory {

    public static Troca criarTrocaValida() {

        Troca troca = new Troca();

        troca.setComentario("Troca de plantão");

        troca.setTipo(TipoTroca.SIMPLES);

        troca.setStatus(StatusTroca.PENDENTE);

        troca.setDataSolicitacao(LocalDateTime.now());

        troca.setProfissionalOrigem(new Profissional());

        troca.setProfissionalDestino(new Profissional());

        troca.setPlantaoOrigem(new Plantao());

        troca.setPlantaoDestino(new Plantao());

        return troca;
    }

    public static Troca criarOutraTrocaValida() {

        Troca troca = new Troca();

        troca.setComentario("Troca noturna UTI");

        troca.setTipo(TipoTroca.DUPLA);

        troca.setStatus(StatusTroca.CONFIRMADO);

        troca.setDataSolicitacao(
                LocalDateTime.now().minusDays(2));

        troca.setDataResposta(
                LocalDateTime.now().minusDays(1));

        troca.setProfissionalOrigem(
                ProfissionalFactory.criarOutroProfissionalValido());

        troca.setProfissionalDestino(
                ProfissionalFactory.criarProfissionalValido());

        troca.setPlantaoOrigem(
                PlantaoFactory.criarOutroPlantaoValido());

        troca.setPlantaoDestino(
                PlantaoFactory.criarPlantaoValido());

        return troca;
    }

    public static Troca criarTrocaSimples() {

        Troca troca = criarTrocaValida();

        troca.setTipo(TipoTroca.SIMPLES);

        return troca;
    }

    public static Troca criarTrocaDupla() {

        Troca troca = criarTrocaValida();

        troca.setTipo(TipoTroca.DUPLA);

        return troca;
    }

    public static Troca criarTrocaConfirmada() {

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.CONFIRMADO);

        troca.setDataResposta(LocalDateTime.now());

        return troca;
    }

    public static Troca criarTrocaPendente() {

        Troca troca = criarTrocaValida();

        troca.setStatus(StatusTroca.PENDENTE);

        return troca;
    }
}