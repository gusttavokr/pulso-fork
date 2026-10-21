package web.pulso.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação de um usuário no sistema.")
public class UsuarioRequestDTO {

    @NotBlank(message = "O campo 'email' é obrigatório.")
    @Email(message = "O campo 'email' deve ser um email válido.")
    @Schema(description = "Email de acesso do usuário.", example = "usuario@pulso.com")
    private String email;

    @NotBlank(message = "O campo 'senha' é obrigatório.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    @Schema(description = "Senha de acesso do usuário, com no mínimo 6 caracteres.", example = "minhaSenhaSegura123")
    private String senha;
}
