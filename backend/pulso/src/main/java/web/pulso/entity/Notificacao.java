package web.pulso.entity;

import jakarta.persistence.*;
import lombok.*;
import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.entity.enums.TipoNotificacao;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "notificacoes")
public class Notificacao extends BaseEntity {

    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String conteudo;

    @ManyToOne
    @JoinColumn(name = "id_remetente", nullable = false)
    private Usuario remetente;

    @ManyToOne
    @JoinColumn(name = "id_destinatario", nullable = false)
    private Usuario destinatario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotificacao status;

    @Column(name = "data_leitura")
    private LocalDateTime dataLeitura;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = StatusNotificacao.PENDENTE;
        }
        super.aoCriar();
    }
}