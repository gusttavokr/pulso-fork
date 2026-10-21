package web.pulso.dto.response.usuario;

import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.dto.response.instituicao.InstituicaoSimpleResponseDTO;
import lombok.AllArgsConstructor;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String email;
    private Boolean ativo;
    private Date ultimoLogin;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private InstituicaoSimpleResponseDTO instituicao;
    private ProfissionalSimpleResponseDTO profissional;
}
