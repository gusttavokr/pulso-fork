package web.pulso.dto.request;

import web.pulso.entity.enums.FormaPagamento;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de criação de um novo plantão.")
public class PlantaoRequestDTO {

    @NotNull(message = "O campo 'horaEntrada' é obrigatório.")
    @Schema(description = "Data e hora de início do plantão.", example = "2025-03-10T07:00:00")
    private LocalDateTime horaEntrada;

    @NotNull(message = "O campo 'horaSaida' é obrigatório.")
    @Schema(description = "Data e hora de término do plantão.", example = "2025-03-10T19:00:00")
    private LocalDateTime horaSaida;

    @PositiveOrZero(message = "O campo 'estimativaValor' deve ser maior que zero.")
    @Schema(description = "Valor estimado para o pagamento do plantão.", example = "1200")
    private int estimativaValor;

    @Schema(description = "Forma de pagamento do plantão (por exemplo: PIX, DINHEIRO, TRANSFERENCIA).", example = "PIX")
    private FormaPagamento formaPagamento;

    @Schema(description = "Data estimada para o pagamento do plantão.", example = "2025-03-15")
    private LocalDate estimativaPagamento;

    @Schema(description = "Comentário adicional sobre o plantão.", example = "Plantão noturno com alta demanda.")
    private String comentario;

    @NotNull(message = "O campo 'quantidadeProfissionais' é obrigatório.")
    @Min(value = 1, message = "O campo 'quantidadeProfissionais' deve ser no mínimo 1.")
    @Schema(description = "Quantidade de profissionais necessários para o plantão.", example = "2")
    private Integer quantidadeProfissionais;

    @NotNull(message = "O campo 'escalaId' é obrigatório.")
    @Schema(description = "ID da escala à qual o plantão está vinculado.", example = "8")
    private Long escalaId;
}