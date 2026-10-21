package web.pulso.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação de uma solicitação de troca de plantão entre profissionais.")
public class TrocaRequestDTO {

    @Size(max = 255, message = "O campo 'comentario' deve ter no máximo 255 caracteres.")
    @Schema(description = "Comentário opcional adicionando contexto ou justificativa para a troca.", example = "Preciso trocar este plantão pois terei um compromisso pessoal.")
    private String comentario;

    @NotNull(message = "O campo 'plantaoOrigemId' é obrigatório.")
    @Schema(description = "ID do plantão que o profissional deseja trocar.", example = "15")
    private Long plantaoOrigemId;

    @Schema(description = "ID do profissional que será convidado para assumir o plantão (opcional).", example = "12")
    private Long profissionalDestinoId;

    @Schema(description = "ID do plantão proposto pelo profissional de destino (opcional).", example = "18")
    private Long plantaoDestinoId;
}