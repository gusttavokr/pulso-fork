package web.pulso.service.patterns.factory.generatePlantaoStrategy;

import web.pulso.entity.Plantao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GeracaoPlantoesStrategy {

    List<Plantao> gerar(
            LocalDate mesReferencia,
            Map<?, Plantao> contexto);
}