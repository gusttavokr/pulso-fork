package web.pulso.dto.response.instituicao;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstituicaoSimpleResponseDTO {
    private Long id;
    private String nome;
    private List<String> emails;
    private String endereco;
    private String cnpj;
}