package web.pulso.entity;

import jakarta.persistence.*;
import lombok.*;
import web.pulso.exception.business.BadRequestException;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.audit.annotation.Auditable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Auditable
@Table(
        name = "escalas",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"setor_id", "mes_referencia"})
        },
        indexes = {
                @Index(name = "idx_escala_mes_referencia", columnList = "mes_referencia"),
                @Index(name = "idx_escala_status", columnList = "status"),
                @Index(name = "idx_escala_setor", columnList = "setor_id"),
                @Index(name = "idx_escala_lider", columnList = "profissional_lider_escala_id"),
        }
)
public class Escala extends BaseEntity{


    @Column(nullable = false)
    private String nome;

    @Column(name = "mes_referencia", nullable = false)
    private LocalDate mesReferencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEscala status;

    @Column(name = "publicada_em")
    private LocalDateTime publicadaEm;

    @ManyToOne
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    @ManyToOne
    @JoinColumn(name = "profissional_lider_escala_id", nullable = false)
    private Profissional liderEscala;

    @OneToMany(mappedBy = "escala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plantao> plantoes;

    @PrePersist
    protected void aoCriar() {
        valido();
        if (status == null)
            status = StatusEscala.RASCUNHO;
        super.aoCriar();
    }

    @PreUpdate
    protected void aoAtualizar() {
        valido();
        super.aoAtualizar();
    }

    public void publicar(Boolean publicar) {
        if (Boolean.FALSE.equals(publicar)) {
            throw new BadRequestException("Uma escala publicada não pode ser despublicada.");
        }
        if (!(status == StatusEscala.RASCUNHO)) {
            throw new BadRequestException("só é possivel publicar escalas em rascunho");
        }
        if (!(plantoes != null && !plantoes.isEmpty())) {
            throw new BadRequestException("Adicione plantões antes de publicar a escala.");
        }

        this.status = StatusEscala.PUBLICADA;
        this.publicadaEm = LocalDateTime.now();
    }
    public boolean isPublicada(){
        return status.equals(StatusEscala.PUBLICADA);
    }

    public boolean podeSerExcluida() {
        return status == StatusEscala.RASCUNHO;
    }

    private void valido() {
        if (nome == null || nome.trim().length() < 3) {
            throw new BadRequestException("O nome da escala deve ter pelo menos 3 letras.");
        }
        var agora = LocalDate.now();

        if (mesReferencia.getYear() < agora.getYear() ||
                (mesReferencia.getYear() == agora.getYear() && mesReferencia.getMonthValue() < agora.getMonthValue())) {
            throw new BadRequestException("O mês de referência não pode estar no passado.");
        }
        if (!podeCriarComAntecedencia()) {
            throw new BadRequestException("Não é permitido criar escalas com menos de 7 dias de antecedência");
        }
    }

    public boolean podeCriarComAntecedencia() {
        LocalDate inicioMes = mesReferencia.withDayOfMonth(1);
        return !inicioMes.atStartOfDay().isBefore(LocalDateTime.now().plusDays(7));
    }

    public void validarAlteracoesPermitidas(String novoNome, Long novoSetorId, LocalDate novoMes) {
        if (!this.setor.getId().equals(novoSetorId)) {
            throw new BadRequestException("Não é possível alterar o setor de uma escala.");
        }

        if (!this.mesReferencia.equals(novoMes)) {
            throw new BadRequestException("Não é permitido alterar o mês de referência da escala.");
        }

        if (this.status == StatusEscala.PUBLICADA) {
            throw new BadRequestException("Não é possível alterar uma escala publicada.");
        }

        if (novoNome != null && novoNome.isBlank()) {
            throw new BadRequestException("O nome da escala não pode ser vazio.");
        }
    }

}