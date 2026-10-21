package web.pulso.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import web.pulso.audit.annotation.Auditable;
import web.pulso.entity.enums.StatusTroca;

import web.pulso.entity.enums.TipoTroca;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "trocas")
@Auditable
public class Troca extends BaseEntity {

    @Column(length = 1000)
    private String comentario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTroca tipo; // SIMPLES ou DUPLA

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTroca status; // CONFIRMADO, PENDENTE, RECUSADO

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    private LocalDateTime dataResposta;

    @ManyToOne
    @JoinColumn(name = "profissional_origem_id", nullable = false)
    private Profissional profissionalOrigem;

    @ManyToOne
    @JoinColumn(name = "profissional_destino_id")
    private Profissional profissionalDestino;

    @ManyToOne
    @JoinColumn(name = "plantao_origem_id", nullable = false)
    private Plantao plantaoOrigem;

    @ManyToOne
    @JoinColumn(name = "plantao_destino_id")
    private Plantao plantaoDestino;

    public void confirmarParaProfissional(Profissional profissional) {
        if (profissional == null)
            throw new IllegalArgumentException("Profissional não pode ser nulo");
        if (StatusTroca.CONFIRMADO.equals(this.status))
            return;

        this.profissionalDestino = profissional;
        this.status = StatusTroca.CONFIRMADO;
    }

    public void validar() {
        validarComentario();
        validarTipo();
        validarStatus();
        validarDataSolicitacao();
        validarProfissionalOrigem();
        validarPlantaoOrigem();
    }

    private void validarComentario() {
        if (comentario != null && comentario.length() > 1000) {
            throw new IllegalArgumentException(
                    "O comentário da troca deve possuir no máximo 1000 caracteres.");
        }
    }

    private void validarTipo() {
        if (tipo == null) {
            throw new IllegalArgumentException(
                    "O tipo da troca é obrigatório.");
        }
    }

    private void validarStatus() {
        if (status == null) {
            throw new IllegalArgumentException(
                    "O status da troca é obrigatório.");
        }
    }

    private void validarDataSolicitacao() {
        if (dataSolicitacao == null) {
            throw new IllegalArgumentException(
                    "A data da solicitação da troca é obrigatória.");
        }
    }

    private void validarProfissionalOrigem() {
        if (profissionalOrigem == null) {
            throw new IllegalArgumentException(
                    "O profissional de origem da troca é obrigatório.");
        }
    }

    private void validarPlantaoOrigem() {
        if (plantaoOrigem == null) {
            throw new IllegalArgumentException(
                    "O plantão de origem da troca é obrigatório.");
        }
    }
}