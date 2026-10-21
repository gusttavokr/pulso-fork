package web.pulso.dto.response.setor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.entity.enums.Especialidade;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetorSimpleResponseDTO {
    private Long id;
    private String nome;
    private Especialidade especialidade;
}