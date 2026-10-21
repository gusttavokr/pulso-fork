import { Component, EventEmitter, inject, Input, Output, signal } from "@angular/core";
import { FiltroContexto } from "../../enums/filtro-contexto";
import { AuthService } from "../../../features/autenticacao/services/auth";
import { PlantaoFiltro } from "../../../features/plantoes/models/plantao-filtro";
import { ButtonComponent } from "../button/button";
import { FiltroCategoriaComponent } from "./components/filtro-categoria/filtro-categoria";
import { FiltroDataComponent } from "./components/filtro-data/filtro-data";
import { FiltroEspecialidadeComponent } from "./components/filtro-especialidade/filtro-especialidade";
import { FiltroHorarioComponent } from "./components/filtro-horario/filtro-horario";
import { FiltroMesReferenciaComponent } from "./components/filtro-mes-referencia/filtro-mes-referencia";
import { FiltroPrecoComponent } from "./components/filtro-preco/filtro-preco";
import { FiltroStatusEscalaComponent } from "./components/filtro-status-escala/filtro-status-escala";
import { FiltroVisualizerComponent } from "./components/filtro-visualizer/filtro-visualizer";
import { VisualizacaoTipo } from "../../enums/visualizacao-tipo";
import { EscalaFiltro } from "../../../features/escalas/models/escala-filtro";
import { CategoriaFiltro } from "../../enums/categoria-filtro";
import { Mes } from "../../enums/mes";

export interface Filtro {

    data?: string; // yyyy-MM-dd
    especialidade?: string;

    preco?: {
        min?: number;
        max?: number;
    };

    horarios?: {
        inicio: string;
        fim: string;
    }[];

    categorias?: CategoriaFiltro[];

    mes?: Mes; // yyyy-MM
    status?: string;

    visualizacao?: VisualizacaoTipo;
}

@Component({
    selector: 'app-filtros',
    imports: [
        FiltroCategoriaComponent,
        FiltroDataComponent,
        FiltroEspecialidadeComponent,
        FiltroHorarioComponent,
        FiltroMesReferenciaComponent,
        FiltroPrecoComponent,
        FiltroStatusEscalaComponent,
        FiltroVisualizerComponent,
        ButtonComponent
    ],
    templateUrl: 'filters.html'
})
export class FiltrosComponent {

    protected readonly authService = inject(AuthService);

    /**
     * Define o contexto de uso do filtro.
     * Controla quais filtros aparecem e qual tipo será emitido.
     */
    @Input({ required: true }) contexto!: FiltroContexto;

    /**
     * Emite filtros aplicados para plantões.
     */
    @Output() plantoesFiltrados = new EventEmitter<PlantaoFiltro>();

    /**
     * Emite filtros aplicados para escalas.
     */
    @Output() escalasFiltradas = new EventEmitter<EscalaFiltro>();

    /**
     * Controla o modo de visualização (UI).
     * Não faz parte dos filtros (não depende do botão "Aplicar").
     */
    @Output() visualizacaoChange = new EventEmitter<VisualizacaoTipo>();

    filtros = signal<Partial<Filtro>>({});
    resetTrigger = signal(0);

    // Contexto
    get isPlantao() {
        return this.contexto === FiltroContexto.MEUS_PLANTOES
            || this.contexto === FiltroContexto.PLANTOES_ABERTOS;
    }

    get isEscala() {
        return this.contexto === FiltroContexto.MINHAS_ESCALAS;
    }

    get showVisualizer() {
        return this.contexto === FiltroContexto.MEUS_PLANTOES && this.authService.isLiderEscala;
    }

    // Entrada dos filtros
    onFiltroChange(parcial: Partial<Filtro>) {
        this.filtros.update(f => ({ ...f, ...parcial }));

        if (parcial.visualizacao !== undefined) {
            this.visualizacaoChange.emit(parcial.visualizacao);
        }
    }

    // Aplicação dos filtros
    aplicarFiltros() {

        const filtros = this.filtros();

        if (this.isPlantao) {

            const categorias = filtros.categorias ?? [];

            const horariosInicio = filtros.horarios?.map(h => h.inicio);
            const horariosFim = filtros.horarios?.map(h => h.fim);

            const resultado: PlantaoFiltro = {
                data: filtros.data,
                especialidade: filtros.especialidade,

                precoMin: filtros.preco?.min,
                precoMax: filtros.preco?.max,

                horariosInicio: horariosInicio?.length ? horariosInicio : undefined,
                horariosFim: horariosFim?.length ? horariosFim : undefined,

                // 🔥 conversão correta das categorias
                ehNovoPlantao: categorias.includes(CategoriaFiltro.NOVO_PLANTAO) || undefined,
                ehRepasse: categorias.includes(CategoriaFiltro.REPASSE) || undefined,
                ehTroca: categorias.includes(CategoriaFiltro.TROCA) || undefined
            };

            this.plantoesFiltrados.emit(resultado);
        }

        if (this.isEscala) {

            const resultado: EscalaFiltro = {
                especialidade: filtros.especialidade,
                mesReferencia: filtros.mes ? String(filtros.mes) : undefined,
                status: filtros.status
            };

            this.escalasFiltradas.emit(resultado);
        }
    }

    // Limpeza
    limparFiltros() {

        const inicial: Partial<Filtro> = {};

        this.filtros.set(inicial);

        this.resetTrigger.update(v => v + 1);

        this.aplicarFiltros();
    }

    svg = `
    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#ffffff" viewBox="0 0 256 256"><path d="M32,80a8,8,0,0,1,8-8H77.17a28,28,0,0,1,53.66,0H216a8,8,0,0,1,0,16H130.83a28,28,0,0,1-53.66,0H40A8,8,0,0,1,32,80Zm184,88H194.83a28,28,0,0,0-53.66,0H40a8,8,0,0,0,0,16H141.17a28,28,0,0,0,53.66,0H216a8,8,0,0,0,0-16Z"></path></svg>`
}