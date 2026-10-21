package web.pulso.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de alocação de profissional em plantão.")
public class AlocacaoRequestDTO {

    @Schema(description = "ID do profissional a ser combinado no plantão.", example = "15")
    private Long idProfissional;

    @NotNull(message = "O ID do plantão é obrigatório.")
    @Schema(description = "ID do plantão em que o profissional será alocado.", example = "3")
    private Long idPlantao;
}
