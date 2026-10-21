package web.pulso.dto.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;
import web.pulso.entity.enums.UF;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação ou atualização de um profissional.")
public class ProfissionalRequestDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Size(min = 3, max = 100, message = "O campo 'nome' deve ter entre 3 e 100 caracteres.")
    @Schema(description = "Nome completo do profissional.", example = "Dra. Ana Beatriz Silva")
    private String nome;

    // E-mail para contato, se informado é atualização, se não, é criação
    @Schema(description = "Lista de e-mails de contato do profissional.", example = "[\"ana.silva@hospital.com\", \"ana.silva@consultorio.com\"]")
    private List<@NotBlank(message = "O e-mail não pode estar em branco.") @Email(message = "E-mail inválido.") String> emails;

    @Size(max = 20, message = "O campo 'telefone' deve ter no máximo 20 caracteres.") // Se for necessário trocar o
                                                                                      // tamanho do telefone, alter aqui
    @Schema(description = "Telefone de contato do profissional.", example = "+55 11 98888-7777")
    private String telefone;

    @Schema(description = "URL da foto de perfil do profissional.", example = "https://cdn.meusistema.com/perfis/ana-silva.png")
    private String fotoPerfil;

    @NotNull(message = "O campo 'anoFormacao' é obrigatório.")
    @PastOrPresent(message = "O campo 'anoFormacao' deve ser uma data no passado ou presente.")
    @Schema(description = "Ano de formação do profissional.", example = "2015-12-01")
    private LocalDate anoFormacao;

    @NotBlank(message = "O campo 'pix' é obrigatório.")
    @Size(max = 100, message = "O campo 'pix' deve ter no máximo 100 caracteres.")
    @Schema(description = "Chave PIX cadastrada pelo profissional.", example = "ana.silva@gmail.com")
    private String pix;

    @Schema(description = "Tipo de chave PIX utilizada pelo profissional (EMAIL, CPF, TELEFONE, ALEATORIA).", example = "EMAIL")
    private TipoPix tipoPix;

    @NotBlank(message = "O número do CRM é obrigatório.")
    @Pattern(regexp = "^[0-9]{4,15}$", message = "O número do CRM deve conter entre 4 e 15 dígitos.")
    @Schema(description = "Número base do CRM.", example = "12345")
    private String crmNumero;

    @NotEmpty(message = "Informe ao menos um estado para o CRM.")
    @Schema(description = "Estados onde o CRM é válido.", example = "[\"SP\", \"RJ\"]")
    private Set<@NotNull UF> crms;

    @NotNull(message = "O campo 'dataNasc' é obrigatório.")
    @Past(message = "O campo 'dataNasc' deve ser uma data no passado.")
    @Schema(description = "Data de nascimento do profissional.", example = "1990-04-12")
    private LocalDate dataNasc;

    @NotEmpty(message = "Informe ao menos uma especialidade.")
    @Schema(description = "Lista de especialidades do profissional.", example = "[\"CARDIOLOGIA\", \"CLINICA_GERAL\"]")
    private Set<@NotNull Especialidade> especialidades;
}