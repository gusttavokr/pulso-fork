package web.pulso.dto.filtros;
import lombok.*;
import web.pulso.entity.enums.Especialidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PlantaoFilter implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idUsuario;
    private LocalDate data;
    private Especialidade especialidade;
    private Integer precoMin;
    private Integer precoMax;
    private List<LocalTime> horariosInicio;
    private List<LocalTime> horariosFim;
    private Boolean ehNovoPlantao;
    private Boolean ehRepasse;
    private Boolean ehTroca;
    private Boolean abertos;
    private Boolean profissional;
    private Boolean liderEscala;
    private Boolean instituicao;

}