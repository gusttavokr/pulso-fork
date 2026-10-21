package web.pulso.dto.response.profissional;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.profissional.estatistica.EstatisticaPlantoesAno;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalEstatisticaResponseDTO {
    private Long id;
    private String foto;
    private String nome;
    private List<String> emails;
    private String telefone;
    private EstatisticaPlantoesAno estatisticasPlantoes;
}
