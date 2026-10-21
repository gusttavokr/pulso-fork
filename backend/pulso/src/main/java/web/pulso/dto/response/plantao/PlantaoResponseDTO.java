package web.pulso.dto.response.plantao;


import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.FormaPagamento;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.entity.enums.TipoTroca;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantaoResponseDTO {
        private Long id;
        private Especialidade especialidade;
        private String nomeInstituicao;
        private LocalDateTime horaEntrada;
        private LocalDateTime horaSaida;
        private Long idLider;
        private String fotoLider;
        private String nomeLider;
        private String nomeSetor;
        private String enderecoInstituicao;
        private String comentario;
        private FormaPagamento formaPagamento;
        private int estimativaValor;
        private String comentarioTroca;
        private TipoTroca tipoTroca;
        private Long idEscala;
        private StatusEscala statusEscala;
        private Integer quantidadeProfissionais;
        private LocalDate estimativaPagamento;

        private List<ProfissionalSimpleResponseDTO> profissionaisCombinados;
        private List<ProfissionalSimpleResponseDTO> profissionaisConfirmados;
        private List<ProfissionalSimpleResponseDTO> profissionaisTrocaDestino;
}
