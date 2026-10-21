package web.pulso.dto.response.instituicao.status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstituicaoProfissionaisStatusResponseDTO {
    private Long total;
    private Long alocados;
    private Long naoAlocados;
}
