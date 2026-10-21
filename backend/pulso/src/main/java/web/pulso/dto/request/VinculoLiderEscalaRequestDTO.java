package web.pulso.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para vincular um líder de escala a uma instituição, especificando o tipo de vínculo.")
public class VinculoLiderEscalaRequestDTO {

    @NotNull(message = "O ID do líder de escala é obrigatório.")
    @Schema(description = "ID do líder de escala a ser vinculado à instituição.", example = "42")
    private Long liderEscalaId;
}
