package web.pulso.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import web.pulso.entity.enums.Especialidade;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "setores")
public class Setor extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "instituicao_id", nullable = false) // uma instituição pode ter muitos setores
    private Instituicao instituicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidade especialidade;

    @OneToMany(mappedBy = "setor")
    private List<Escala> escalas;

    public void validar(){
        
    }
}