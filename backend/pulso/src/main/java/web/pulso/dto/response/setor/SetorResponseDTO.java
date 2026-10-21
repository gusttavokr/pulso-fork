package web.pulso.dto.response.setor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import web.pulso.entity.enums.Especialidade;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetorResponseDTO {

    private Long id;
    private String nome;
    private Especialidade especialidade;

    private InstituicaoSimpleResponseDTO instituicao;
    private List<EscalaSimpleResponseDTO> escalas;
}
