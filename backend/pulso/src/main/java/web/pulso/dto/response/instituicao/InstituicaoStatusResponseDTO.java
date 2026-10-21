package web.pulso.dto.response.instituicao;

import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.instituicao.status.InstituicaoProfissionaisStatusResponseDTO;

@Data
@NoArgsConstructor
public class InstituicaoStatusResponseDTO {
    private InstituicaoProfissionaisStatusResponseDTO profissionais;
}
