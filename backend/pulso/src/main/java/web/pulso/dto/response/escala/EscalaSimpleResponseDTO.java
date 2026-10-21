package web.pulso.dto.response.escala;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.entity.enums.StatusEscala;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalaSimpleResponseDTO {
    private Long id;
    private String nome;
    private LocalDate mesReferencia;
    private StatusEscala status;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    private String nomeLider;
    private String fotoLider;
}
