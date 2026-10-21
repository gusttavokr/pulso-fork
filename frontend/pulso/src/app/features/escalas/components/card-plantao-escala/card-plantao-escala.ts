import { Component, computed, EventEmitter, Input, Output } from "@angular/core";
import { PlantaoEscalaSimple } from "../../../plantoes/models/plantao-escala-simple";
import { ESPECIALIDADE_LABEL } from "../../../../shared/enums/especialidade";

@Component({
    selector: 'app-card-plantao-escala',
    imports: [],
    templateUrl: 'card-plantao-escala.html'
})
export class CardPlantaoEscalaComponent {

    @Input({ required: true })
    plantao!: PlantaoEscalaSimple;

    @Output()
    clicked = new EventEmitter<MouseEvent>();

    readonly quantidadeProfissionais = computed(() =>
        this.plantao.profissionaisAlocados.length
    );

    readonly profissionaisVisiveis = computed(() =>
        this.plantao.profissionaisAlocados.slice(0, 3)
    );

    readonly labelEspecialidade = computed(() =>
        ESPECIALIDADE_LABEL[this.plantao.especialidade]
    );

    readonly horarioFormatado = computed(() =>
        new Date(this.plantao.horaEntrada).toLocaleTimeString('pt-BR', {
            hour: '2-digit',
            minute: '2-digit'
        })
    );

    handleClick(event: MouseEvent): void {
        event.stopPropagation();
        this.clicked.emit(event);
    }

}