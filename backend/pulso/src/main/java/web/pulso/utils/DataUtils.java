package web.pulso.utils;

import web.pulso.exception.business.BadRequestException;

import java.time.LocalDate;
import java.time.Year;

public class DataUtils {
    public static LocalDate converterMesReferenciaParaData(String mesReferencia) {
        if (mesReferencia == null)
            return null;

        if (!mesReferencia.matches("^(0[1-9]|1[0-2])$")) {
            throw new BadRequestException("Mês de referência inválido");
        }

        int mes = Integer.parseInt(mesReferencia);
        int anoAtual = Year.now().getValue();

        return LocalDate.of(anoAtual, mes, 1);
    }
}
