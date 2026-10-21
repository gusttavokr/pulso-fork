import { CommonModule } from "@angular/common";
import { Component, EventEmitter, Output, signal } from "@angular/core";
import { VISUALIZACAO_CONFIG, VisualizacaoTipo } from "../../../../enums/visualizacao-tipo";

@Component({
    selector: 'app-card-visualizer',
    imports: [CommonModule],
    templateUrl: 'filtro-visualizer.html'
})
export class FiltroVisualizerComponent {

    @Output() filtroChange = new EventEmitter<{
        visualizacao?: VisualizacaoTipo;
    }>();

    visualizacao = signal<VisualizacaoTipo>(VisualizacaoTipo.LIDER_ESCALA);

    tipos = Object.values(VisualizacaoTipo);

    selecionar(tipo: VisualizacaoTipo) {
        this.visualizacao.set(tipo);
        this.filtroChange.emit({ visualizacao: tipo });
    }

    getLabel(tipo: VisualizacaoTipo) {
        return VISUALIZACAO_CONFIG[tipo].label;
    }

}