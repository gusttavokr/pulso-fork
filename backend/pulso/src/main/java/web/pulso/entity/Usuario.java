package web.pulso.entity;

import jakarta.persistence.*;
import lombok.*;
import web.pulso.audit.annotation.AuditIgnore;
import web.pulso.audit.annotation.Auditable;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuarios")
@Auditable
public class Usuario extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @AuditIgnore
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "ultimo_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoLogin;

    @OneToOne(mappedBy = "usuario", optional = true)
    private Instituicao instituicao;

    @OneToOne(mappedBy = "usuario", optional = true)
    private Profissional profissional;
   
    @PrePersist
    @PreUpdate
    public void validar() {

        if (email == null)
            throw new IllegalArgumentException(
                "O email do usuário é obrigatório.");

        if (email.isBlank())
            throw new IllegalArgumentException(
                "O email do usuário é obrigatório.");

        if (email.length() > 255)
            throw new IllegalArgumentException(
                "O email do usuário deve possuir no máximo 255 caracteres.");

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
            throw new IllegalArgumentException(
                "O email do usuário possui formato inválido.");

        if (senha == null)
            throw new IllegalArgumentException(
                "A senha do usuário é obrigatória.");

        if (senha.isBlank())
            throw new IllegalArgumentException(
                "A senha do usuário é obrigatória.");

        if (senha.length() < 8)
            throw new IllegalArgumentException(
                "A senha do usuário deve possuir pelo menos 8 caracteres.");

        if (ativo == null)
            throw new IllegalArgumentException(
                "O status do usuário é obrigatório.");

        super.aoCriar();
        super.aoAtualizar();
    }
}