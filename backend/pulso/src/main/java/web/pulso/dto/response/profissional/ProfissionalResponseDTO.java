package web.pulso.dto.response.profissional;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.dto.response.troca.TrocaSimpleResponseDTO;
import web.pulso.dto.response.usuario.UsuarioSimpleResponseDTO;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalResponseDTO {
    private Long id;
    private String nome;
    private List<String> emails;
    private String telefone;
    private String fotoPerfil;
    private LocalDate anoFormacao;
    private String pix;
    private TipoPix tipoPix;
    private List<String> crmsFormatados;
    private LocalDate dataNasc;
    private List<Especialidade> especialidades;

    private UsuarioSimpleResponseDTO usuario;
    private List<InstituicaoSimpleResponseDTO> instituicoesClt;
    private List<InstituicaoSimpleResponseDTO> instituicoesPj;
    private List<InstituicaoSimpleResponseDTO> instituicoesLiderEscala;
    private List<EscalaSimpleResponseDTO> escalas;
    private List<PlantaoSimpleResponseDTO> plantoesCombinados;
    private List<PlantaoSimpleResponseDTO> plantoesConfirmados;
    private List<TrocaSimpleResponseDTO> trocasOrigem;
    private List<TrocaSimpleResponseDTO> trocasDestino;
}