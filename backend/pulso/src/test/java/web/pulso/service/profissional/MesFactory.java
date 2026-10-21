package web.pulso.service.profissional;

import web.pulso.dto.response.profissional.estatistica.Mes;

public class MesFactory {

    public static Mes criarMesJaneiroValido() {

        Mes mes = new Mes();

        mes.setMes(1);

        mes.setQuantidade(10);

        return mes;
    }

    public static Mes criarMesFevereiroValido() {

        Mes mes = new Mes();

        mes.setMes(2);

        mes.setQuantidade(8);

        return mes;
    }

    public static Mes criarMesMarcoValido() {

        Mes mes = new Mes();

        mes.setMes(3);

        mes.setQuantidade(12);

        return mes;
    }

    public static Mes criarMesSemQuantidade() {

        Mes mes = criarMesJaneiroValido();

        mes.setQuantidade(null);

        return mes;
    }

    public static Mes criarMesSemNumeroMes() {

        Mes mes = criarMesJaneiroValido();

        mes.setMes(null);

        return mes;
    }
}