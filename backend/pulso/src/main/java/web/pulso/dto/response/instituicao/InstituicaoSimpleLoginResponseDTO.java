package web.pulso.dto.response.instituicao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstituicaoSimpleLoginResponseDTO {
    private Long id;
    private String nome;
    private String fotoPerfil;
}
