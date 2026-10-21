package web.pulso.dto.response.profissional.estatistica;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaPlantoesAno {
    private Integer ano;
    private List<Mes> meses;
}
