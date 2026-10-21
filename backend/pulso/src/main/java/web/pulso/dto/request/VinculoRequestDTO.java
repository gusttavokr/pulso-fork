package web.pulso.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para vincular um profissional a uma instituição, especificando o tipo de vinculo.")
public class VinculoRequestDTO {

    @NotNull(message = "O ID do profissional é obrigatório.")
    @Schema(description = "ID do profissional a ser vinculado à instituição.", example = "42")
    private Long profissionalId;

    @NotNull(message = "O tipo de vínculo (CLT ou PJ) deve ser informado.")
    @Schema(description = "Define se o vínculo é PJ (true) ou CLT (false).", example = "true")
    private Boolean isPj;
}
