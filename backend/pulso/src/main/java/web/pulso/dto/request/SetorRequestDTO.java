package web.pulso.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.entity.enums.Especialidade;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação ou atualização de um setor dentro de uma instituição.")
public class SetorRequestDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Size(max = 100, message = "O campo 'nome' deve ter no máximo 100 caracteres.")
    @Schema(description = "Nome do setor dentro da instituição.", example = "Emergência Adulto")
    private String nome;

    @NotNull(message = "O campo 'especialidade' é obrigatório.")
    @Schema(description = "Especialidade principal do setor.", example = "CLINICA_GERAL")
    private Especialidade especialidade;
}