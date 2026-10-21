package web.pulso.dto.response.troca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrocaSimpleResponseDTO {
    private Long id;
    private String comentario;
    private TipoTroca tipo;
    private StatusTroca status;
}
