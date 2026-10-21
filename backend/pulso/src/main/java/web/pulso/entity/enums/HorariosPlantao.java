package web.pulso.entity.enums;

import java.time.LocalTime;

public enum HorariosPlantao {
    MANHA(LocalTime.of(7, 0), LocalTime.of(13, 0)),
    TARDE(LocalTime.of(13, 0), LocalTime.of(19, 0)),
    NOITE(LocalTime.of(19, 0), LocalTime.of(7, 0));

    private final LocalTime inicio;
    private final LocalTime fim;

    HorariosPlantao(LocalTime inicio, LocalTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }
}
