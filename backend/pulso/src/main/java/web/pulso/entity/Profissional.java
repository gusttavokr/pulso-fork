package web.pulso.entity;

import jakarta.persistence.*;
import lombok.*;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.TipoPix;
import web.pulso.entity.enums.UF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "profissionais")
public class Profissional extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @ElementCollection
    @CollectionTable(name = "profissional_emails", joinColumns = @JoinColumn(name = "profissional_id"))
    @Column(name = "email", nullable = false)
    private List<String> emails;

    private String telefone;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @Column(name = "ano_formacao", nullable = false)
    private LocalDate anoFormacao;

    @Column(unique = true)
    private String pix;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pix")
    private TipoPix tipoPix;

    @Column(nullable = false)
    private String crmNumero;

    @ElementCollection
    @CollectionTable(name = "profissional_crms", joinColumns = @JoinColumn(name = "profissional_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "profissional_id", "uf" }))
    @Column(name = "uf", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<UF> crms;

    @Column(name = "data_nasc", nullable = false)
    private LocalDate dataNasc;

    @ElementCollection(targetClass = Especialidade.class)
    @CollectionTable(name = "profissional_especialidades", joinColumns = @JoinColumn(name = "profissional_id"))
    @Column(name = "especialidade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Especialidade> especialidades;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "profissionalOrigem", fetch = FetchType.LAZY)
    private List<Troca> trocasOrigem;

    @OneToMany(mappedBy = "profissionalDestino", fetch = FetchType.LAZY)
    private List<Troca> trocasDestino;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profissional_instituicao_clt", joinColumns = @JoinColumn(name = "profissional_id"), inverseJoinColumns = @JoinColumn(name = "instituicao_id"))
    private List<Instituicao> instituicoesClt = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profissional_instituicao_pj", joinColumns = @JoinColumn(name = "profissional_id"), inverseJoinColumns = @JoinColumn(name = "instituicao_id"))
    private List<Instituicao> instituicoesPj = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profissional_instituicao_lider_escala", joinColumns = @JoinColumn(name = "profissional_id"), inverseJoinColumns = @JoinColumn(name = "instituicao_id"))
    private List<Instituicao> instituicoesLiderEscala = new ArrayList<>();

    @OneToMany(mappedBy = "liderEscala")
    private List<Escala> escalas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profissional_plantao_combinado", joinColumns = @JoinColumn(name = "profissional_id"), inverseJoinColumns = @JoinColumn(name = "plantao_id"))
    private List<Plantao> plantoesCombinados = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profissional_plantao_confirmado", joinColumns = @JoinColumn(name = "profissional_id"), inverseJoinColumns = @JoinColumn(name = "plantao_id"))
    private List<Plantao> plantoesConfirmados = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void validar() {

        if (anoFormacao.isAfter(LocalDate.now()))
            throw new IllegalArgumentException(
                    "O ano de formação não pode ser no futuro.");

        if (dataNasc.isAfter(LocalDate.now().minusYears(18)))
            throw new IllegalArgumentException(
                    "O profissional deve ter pelo menos 18 anos de idade.");

        if (anoFormacao.isBefore(dataNasc))
            throw new IllegalArgumentException(
                    "O ano de formação não pode ser anterior à data de nascimento.");

        if (anoFormacao.isBefore(dataNasc.plusYears(18)))
            throw new IllegalArgumentException(
                    "O profissional não pode ter se formado antes dos 18 anos.");

        if (dataNasc.isBefore(LocalDate.now().minusYears(120)))
            throw new IllegalArgumentException(
                    "A idade do profissional é inválida.");

        super.aoCriar();
        super.aoAtualizar();
    }

    public boolean eLiderDaInstituicao(Instituicao instituicao) {
        return instituicoesLiderEscala.stream()
                .anyMatch(inst -> inst.getId().equals(instituicao.getId()));
    }

    public boolean eLiderNoSetor(Setor setor) {
        boolean mesmaInstituicao = instituicoesLiderEscala.stream()
                .anyMatch(inst -> inst.getId().equals(setor.getInstituicao().getId()));

        boolean mesmaEspecialidade = especialidades.stream()
                .anyMatch(e -> e.equals(setor.getEspecialidade()));

        return mesmaInstituicao && mesmaEspecialidade;
    }

    public boolean possuiEspecialidadeParaSetor(Setor setor) {

        if (setor == null || setor.getEspecialidade() == null) {
            return false;
        }

        return especialidades.contains(setor.getEspecialidade());
    }

}