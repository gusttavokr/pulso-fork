package web.pulso.service.patterns.factory.generatePlantaoStrategy;


public class GeradorPlantoesFactory {

    public static GeracaoPlantoesStrategy escolherStrategy(Boolean copia) {
        if (Boolean.TRUE.equals(copia)) {

            return new GeracaoPlantoesCopiaStrategy();
        }

        return new GeracaoPlantoesSimplesStrategy();
    }
}
