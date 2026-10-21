package web.pulso.dto.response.plantao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantaoSimpleResponseDTO {
    private Long id;
    private String nomeInstituicao;
    private String fotoLider;
    private String nomeLider;
    private String especialidade;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private int estimativaValor;
    private Boolean ehNovoPlantao;
    private Boolean ehTroca;
    private Boolean ehRepasse;
}
