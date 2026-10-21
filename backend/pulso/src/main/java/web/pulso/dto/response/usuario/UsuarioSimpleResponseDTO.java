package web.pulso.dto.response.usuario;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSimpleResponseDTO {
    private Long id;
    private Boolean ativo;
}