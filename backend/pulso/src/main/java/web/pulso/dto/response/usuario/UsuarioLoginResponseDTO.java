package web.pulso.dto.response.usuario;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.instituicao.InstituicaoSimpleLoginResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleLoginResponseDTO;
import web.pulso.entity.enums.TipoUsuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLoginResponseDTO {
    private Long id;
    private String token;

    private TipoUsuario tipo;
    private List<String> roles;

    private InstituicaoSimpleLoginResponseDTO instituicao;
    private ProfissionalSimpleLoginResponseDTO profissional;
}
