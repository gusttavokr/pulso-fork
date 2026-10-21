package web.pulso.service.profissional;

import java.util.ArrayList;
import java.util.List;

import web.pulso.dto.response.profissional.estatistica.EstatisticaPlantoesAno;

public class EstatisticaPlantoesAnoFactory {

        public static EstatisticaPlantoesAno criarEstatisticaPlantoesAnoValido() {

                EstatisticaPlantoesAno estatistica = new EstatisticaPlantoesAno();

                estatistica.setAno(2026);

                estatistica.setMeses(
                                List.of(
                                                MesFactory.criarMesJaneiroValido(),
                                                MesFactory.criarMesFevereiroValido(),
                                                MesFactory.criarMesMarcoValido()));

                return estatistica;
        }

        public static EstatisticaPlantoesAno criarEstatisticaPlantoesAnoSemAno() {

                EstatisticaPlantoesAno estatistica = criarEstatisticaPlantoesAnoValido();

                estatistica.setAno(null);

                return estatistica;
        }

        public static EstatisticaPlantoesAno criarEstatisticaPlantoesAnoSemMeses() {

                EstatisticaPlantoesAno estatistica = criarEstatisticaPlantoesAnoValido();

                estatistica.setMeses(null);

                return estatistica;
        }

        public static EstatisticaPlantoesAno criarEstatisticaPlantoesAnoComMesesVazios() {

                EstatisticaPlantoesAno estatistica = criarEstatisticaPlantoesAnoValido();

                estatistica.setMeses(new ArrayList<>());

                return estatistica;
        }
}