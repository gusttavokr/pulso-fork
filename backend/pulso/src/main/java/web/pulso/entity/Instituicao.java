package web.pulso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "instituicoes")
public class Instituicao extends BaseEntity{

    @Column(nullable = false)
    private String nome;

    @ElementCollection
    @CollectionTable(name = "instituicao_emails", joinColumns = @JoinColumn(name = "instituicao_id"))
    @Column(name = "email", nullable = false)
    private List<String> emails; // Lista de emails para contato com a instituição

    @Column(nullable = false)
    private String telefone;

    @Column(name = "foto_perfil", nullable = false)
    private String fotoPerfil;

    @Column(unique = true, nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String cnpj;

    @Column(name = "exige_vinculo", nullable = false)
    private Boolean exigeVinculo;

    // Quais os setores dessa instituição
    @OneToMany(mappedBy = "instituicao", fetch = FetchType.LAZY)
    private List<Setor> setores;

    // Qual o perfil de usuário dessa instituição
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(mappedBy = "instituicoesClt", fetch = FetchType.LAZY)
    private List<Profissional> profissionais_clt = new ArrayList<>();

    @ManyToMany(mappedBy = "instituicoesPj", fetch = FetchType.LAZY)
    private List<Profissional> profisionais_pj = new ArrayList<>();

    // Quais profissionais são líderes de escala nessa instituição
    @ManyToMany(mappedBy = "instituicoesLiderEscala", fetch = FetchType.LAZY)
    private List<Profissional> profissionais_lider_escala = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void validar() {

        validarNome();
        validarEmails();
        validarTelefone();
        validarFotoPerfil();
        validarEndereco();
        validarCnpj();
        validarExigeVinculo();

        super.aoCriar();
        super.aoAtualizar();
    }

    private void validarNome() {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException(
                    "O nome da instituição é obrigatório.");

        if (nome.length() < 3)
            throw new IllegalArgumentException(
                    "O nome da instituição deve possuir pelo menos 3 caracteres.");

        if (nome.length() > 100)
            throw new IllegalArgumentException(
                    "O nome da instituição deve possuir no máximo 100 caracteres.");
    }

    private void validarEmails() {
        if (emails == null || emails.isEmpty())
            throw new IllegalArgumentException(
                    "A instituição deve possuir pelo menos um email.");

        if (emails.size() > 5)
            throw new IllegalArgumentException(
                    "A instituição pode possuir no máximo 5 emails.");

        for (String email : emails) {
            if (email == null)
                throw new IllegalArgumentException(
                        "O email da instituição não pode ser nulo.");

            if (email.isBlank())
                throw new IllegalArgumentException(
                        "O email da instituição é obrigatório.");

            if (email.length() > 255)
                throw new IllegalArgumentException(
                        "O email da instituição deve possuir no máximo 255 caracteres.");

            if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
                throw new IllegalArgumentException(
                        "O email da instituição possui formato inválido.");
        }
    }

    private void validarTelefone() {
        if (telefone == null || telefone.isBlank())
            throw new IllegalArgumentException(
                    "O telefone da instituição é obrigatório.");

        if (telefone.length() > 20)
            throw new IllegalArgumentException(
                    "O telefone da instituição deve possuir no máximo 20 caracteres.");
    }

    private void validarFotoPerfil() {
        if (fotoPerfil == null || fotoPerfil.isBlank())
            throw new IllegalArgumentException(
                    "A foto de perfil da instituição é obrigatória.");
    }

    private void validarEndereco() {
        if (endereco == null || endereco.isBlank())
            throw new IllegalArgumentException(
                    "O endereço da instituição é obrigatório.");

        if (endereco.length() > 255)
            throw new IllegalArgumentException(
                    "O endereço da instituição deve possuir no máximo 255 caracteres.");
    }

    private void validarCnpj() {
        if (cnpj == null || cnpj.isBlank())
            throw new IllegalArgumentException(
                    "O CNPJ da instituição é obrigatório.");

        if (!cnpj.matches("\\d{14}"))
            throw new IllegalArgumentException(
                    "O CNPJ da instituição deve conter exatamente 14 dígitos numéricos.");
    }

    private void validarExigeVinculo() {
        if (exigeVinculo == null)
            throw new IllegalArgumentException(
                    "A informação de exigência de vínculo da instituição é obrigatória.");
    }

    @Transient
    public List<Profissional> getTodosMedicos() {
        return Stream.concat(
                profissionais_clt.stream(),
                profisionais_pj.stream()
        ).distinct().toList();
    }

}
