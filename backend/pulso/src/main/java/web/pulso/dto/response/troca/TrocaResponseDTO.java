package web.pulso.dto.response.troca;

import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrocaResponseDTO {
    private Long id;
    private String comentario;
    private TipoTroca tipo;
    private StatusTroca status;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataResposta;

    private ProfissionalSimpleResponseDTO profissionalOrigem;
    private ProfissionalSimpleResponseDTO profissionalDestino;

    private PlantaoSimpleResponseDTO plantaoOrigem;
    private PlantaoSimpleResponseDTO plantaoDestino;
}