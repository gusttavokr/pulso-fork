package web.pulso.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para responder a uma solicitação de troca, aceitando ou recusando a proposta.")
public class RespostaTrocaRequestDTO {

    @NotNull(message = "O campo 'trocaId' é obrigatório.")
    @Schema(description = "ID da troca que está sendo respondida.", example = "1")
    private Long trocaId;

    @NotNull(message = "Deve ser informado se a troca foi aceita ou recusada.")
    @Schema(description = "Define se a troca foi aceita (true) ou recusada (false).", example = "true")
    private Boolean aceita;

    @Schema(description = "ID do plantão de destino (obrigatório apenas se a troca for aceita).", example = "12")
    private Long plantaoDestinoId;
}