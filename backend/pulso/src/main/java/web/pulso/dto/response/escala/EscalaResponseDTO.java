package web.pulso.dto.response.escala;

import web.pulso.dto.response.plantao.PlantaoEscalaSimplesResponseDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.StatusEscala;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalaResponseDTO {

        private Long id;

        private String nome;
        private String nomeInstituicao;
        private String nomeLider;
        private String fotoLider;
        private String nomeSetor;

        private Especialidade especialidadeSetor;

        private LocalDate mesReferencia;

        private StatusEscala status;

        private List<PlantaoEscalaSimplesResponseDTO> plantoes;
}
