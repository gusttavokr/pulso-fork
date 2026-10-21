package web.pulso.service.patterns.factory.generatePlantaoStrategy;

import web.pulso.entity.*;
import web.pulso.entity.enums.HorariosPlantao;

import java.time.*;
import java.util.*;

public class GeracaoPlantoesSimplesStrategy implements GeracaoPlantoesStrategy {

    @Override
    public List<Plantao> gerar(LocalDate mesReferencia, Map<?, Plantao> contexto) {

        YearMonth anoMes = YearMonth.from(mesReferencia);
        LocalDate inicio = anoMes.atDay(1);
        LocalDate fim = anoMes.atEndOfMonth();

        List<Plantao> plantoes = new ArrayList<>();

        for (LocalDate dia = inicio; !dia.isAfter(fim); dia = dia.plusDays(1)) {
            for (HorariosPlantao horario : HorariosPlantao.values()) {

                LocalDateTime entrada = LocalDateTime.of(dia, horario.getInicio());
                LocalDateTime saida = horario == HorariosPlantao.NOITE
                        ? LocalDateTime.of(dia.plusDays(1), horario.getFim())
                        : LocalDateTime.of(dia, horario.getFim());

                Plantao plantao = new Plantao();
                plantao.setHoraEntrada(entrada);
                plantao.setHoraSaida(saida);
                plantao.setQuantidadeProfissionais(1);

                plantoes.add(plantao);
            }
        }

        return plantoes;
    }
}
