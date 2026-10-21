package web.pulso.dto.response.instituicao;

import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.dto.response.setor.SetorSimpleResponseDTO;
import web.pulso.dto.response.usuario.UsuarioSimpleResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituicaoResponseDTO {
    private Long id;
    private String nome;
    private List<String> emails;
    private String telefone;
    private String fotoPerfil;
    private String endereco;
    private String cnpj;
    private Boolean exigeVinculo;

    private UsuarioSimpleResponseDTO usuario;
    private List<SetorSimpleResponseDTO> setores;
    private List<ProfissionalSimpleResponseDTO> profissionaisClt;
    private List<ProfissionalSimpleResponseDTO> profissionaisPj;
}