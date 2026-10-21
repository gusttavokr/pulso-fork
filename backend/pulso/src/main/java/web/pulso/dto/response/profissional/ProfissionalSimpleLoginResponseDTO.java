package web.pulso.dto.response.profissional;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.entity.enums.Especialidade;

@Data
@NoArgsConstructor
public class ProfissionalSimpleLoginResponseDTO {
    private Long id;
    private String nome;
    private String fotoPerfil;
    private List<Especialidade> especialidades;
}
