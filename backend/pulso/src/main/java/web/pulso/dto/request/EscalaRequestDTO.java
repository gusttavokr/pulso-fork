package web.pulso.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de criação de uma nova escala.")
public class EscalaRequestDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Schema(description = "Nome da escala a ser criada.", example = "Escala de Enfermagem - Março 2025")
    private String nome;

    @NotNull(message = "O campo 'mes' é obrigatório para criar uma escala.")
    @Schema(description = "Mês de referência da escala.", example = "2025-03-01")
    private LocalDate mes;

    @Schema(description = "Indica se a escala deve ser criada como uma cópia de outra.", example = "false")
    private Boolean copia = false;

    @NotNull(message = "O campo 'setorId' é obrigatório para criar uma escala.")
    @Schema(description = "ID do setor ao qual a escala pertence.", example = "2")
    private Long setorId;
}