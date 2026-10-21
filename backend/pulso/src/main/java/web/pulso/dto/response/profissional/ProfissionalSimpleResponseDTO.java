package web.pulso.dto.response.profissional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalSimpleResponseDTO {
    private Long id;
    private String foto;
    private String nome;
}
