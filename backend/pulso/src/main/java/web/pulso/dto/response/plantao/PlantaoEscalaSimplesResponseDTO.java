package web.pulso.dto.response.plantao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.entity.enums.Especialidade;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantaoEscalaSimplesResponseDTO {

    private Long id;

    private String nomeInstituicao;
    private String estimativaValor;
    private String qtdProfissionais;

    private Especialidade especialidade;

    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    private List<ProfissionalSimpleResponseDTO> profissionaisAlocados;
}
