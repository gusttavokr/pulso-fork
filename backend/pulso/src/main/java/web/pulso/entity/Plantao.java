package web.pulso.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import web.pulso.audit.annotation.Auditable;
import web.pulso.entity.enums.FormaPagamento;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.ForbiddenException;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(
        name = "plantoes",
        indexes = {
        @Index(name = "idx_plantoes_escala", columnList = "escala_id"),
        @Index(name = "idx_plantoes_hora_entrada_saida", columnList = "hora_entrada,hora_saida"),
        @Index(name = "idx_plantoes_estimativa_valor", columnList = "estimativa_valor")
    }
)
@Auditable
public class Plantao extends BaseEntity{

    @Column(name = "hora_entrada", nullable = false)
    private LocalDateTime horaEntrada;

    @Column(name = "hora_saida", nullable = false)
    private LocalDateTime horaSaida;

    @Column(name = "estimativa_valor")
    private int estimativaValor;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;

    @Column(name = "estimativa_pagamento")
    private LocalDate estimativaPagamento;

    @Column(length = 1000)
    private String comentario;

    @Column(name = "quantidade_profissionais", nullable = false)
    private Integer quantidadeProfissionais;

    @ManyToOne
    @JoinColumn(name = "escala_id", nullable = false)
    private Escala escala;

    @ManyToMany(mappedBy = "plantoesCombinados", fetch = FetchType.LAZY)
    private Set<Profissional> profissionaisCombinados = new HashSet<>();

    @ManyToMany(mappedBy = "plantoesConfirmados", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private Set<Profissional> profissionaisConfirmados = new HashSet<>();

    @OneToMany(mappedBy = "plantaoOrigem", fetch = FetchType.LAZY)
    private List<Troca> trocasOrigem;

    @OneToMany(mappedBy = "plantaoDestino", fetch = FetchType.LAZY)
    private List<Troca> trocasDestino;

    public Set<Profissional> getProfissionaisAlocados() {
        return Stream.concat(
                profissionaisCombinados == null ? Stream.empty() : profissionaisCombinados.stream(),
                profissionaisConfirmados == null ? Stream.empty() : profissionaisConfirmados.stream()
        ).collect(Collectors.toUnmodifiableSet());
    }

    public void validarPermissaoEditarComentario(Profissional profissional) {
        boolean eLiderNaInstituicao = profissional.getInstituicoesLiderEscala() != null &&
                profissional.getInstituicoesLiderEscala().stream()
                        .anyMatch(inst -> inst.getId().equals(this.escala.getSetor().getInstituicao().getId()));

        if (!eLiderNaInstituicao) {
            throw new ForbiddenException(
                    "Usuário não possui permissão para editar o comentário deste plantão");
        }

        if (this.escala.getStatus() == StatusEscala.PUBLICADA) {
            throw new ConflictException(
                    "Não é possível alterar o comentário de um plantão de uma escala publicada.");
        }
    }

    public void verificarProfissionalJaVinculado(Profissional profissional) {
        if (this.profissionaisConfirmados.contains(profissional) ||
                this.profissionaisCombinados.contains(profissional)) {
            throw new ConflictException(
                    "O profissional já está vinculado a este plantão."
            );
        }
    }

    public void adicionarProfissionalCombinado(Profissional profissional) {
        if (profissional == null) return;
        this.profissionaisCombinados.add(profissional);
        profissional.getPlantoesCombinados().add(this);
    }

    public void removerProfissionalCombinado(Profissional profissional) {
        if (profissional == null) return;
        this.profissionaisCombinados.remove(profissional);
        profissional.getPlantoesCombinados().remove(this);
    }
    public void adicionarProfissionalConfirmado(Profissional profissional) {
        this.profissionaisConfirmados.add(profissional);
        profissional.getPlantoesConfirmados().add(this);
    }

    public void removerProfissionalConfirmado(Profissional profissional) {
        this.profissionaisConfirmados.remove(profissional);
        profissional.getPlantoesConfirmados().remove(this);
    }
    public boolean mesmaEscalaId(Long escalaId) {
        return this.escala.getId().equals(escalaId);
    }

}