package web.pulso.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação ou atualização de uma instituição.")
public class InstituicaoRequestDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Size(min = 3, max = 100, message = "O campo 'nome' deve ter entre 3 e 100 caracteres.")
    @Schema(description = "Nome da instituição.", example = "Hospital Vida Saudável")
    private String nome;

    // E-mail para contato, se informado é atualização, se não, é criação
    @Schema(description = "Lista de e-mails de contato da instituição.", example = "[\"contato@vidasaudavel.com.br\", \"financeiro@vidasaudavel.com.br\"]")
    private List<@NotBlank(message = "O e-mail não pode estar em branco.") @Email(message = "E-mail inválido.") String> emails;

    @NotNull(message = "O campo 'telefone' é obrigatório.")
    @Size(max = 20, message = "O campo 'telefone' deve ter no máximo 20 caracteres.")
    @Schema(description = "Telefone de contato da instituição.", example = "+55 11 99999-8888")
    private String telefone;

    @NotNull(message = "O campo 'fotoPerfil' é obrigatório.")
    @Schema(description = "URL da foto de perfil da instituição.", example = "https://cdn.meusistema.com/imagens/hospital.png")
    private String fotoPerfil;

    @NotNull(message = "O campo 'endereco' é obrigatório.")
    @NotBlank(message = "O campo 'endereco' é obrigatório.")
    @Size(max = 255, message = "O campo 'endereco' deve ter no máximo 255 caracteres.")
    @Schema(description = "Endereço completo da instituição.", example = "Rua das Flores, 123 - Centro, São Paulo - SP")
    private String endereco;

    @NotNull(message = "O campo 'cnpj' é obrigatório.")
    @NotBlank(message = "O campo 'cnpj' é obrigatório.")
    @Pattern(regexp = "\\d{14}", message = "O campo 'cnpj' deve conter exatamente 14 dígitos numéricos (somente números).")
    @Schema(description = "CNPJ da instituição (somente números).", example = "12345678000199")
    private String cnpj;

    @NotNull(message = "O campo 'exigeVinculo' é obrigatório.")
    @Schema(description = "Define se a instituição exige vínculo formal para atuação dos profissionais.", example = "true")
    private Boolean exigeVinculo;
}