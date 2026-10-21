import { Component, inject, OnInit, signal } from "@angular/core";
import { PlantaoSimple } from "../../models/plantao-simple";
import { PlantaoFiltro } from "../../models/plantao-filtro";
import { VisualizacaoTipo } from "../../../../shared/enums/visualizacao-tipo";

import { BreadcrumbModule } from "primeng/breadcrumb";
import { CommonModule } from "@angular/common";
import { FiltrosComponent } from "../../../../shared/components/filters/filters";
import { CardPlantaoComponent } from "../../components/card-plantao/card-plantao";
import { PesquisaComponent } from "../../../../shared/components/pesquisa/pesquisa";
import { PlantaoService } from "../../services/plantao";
import { PlantaoVisualizacaoContexto } from "../../../../shared/enums/plantao-visualizacao-contexto";
import { FiltroContexto } from "../../../../shared/enums/filtro-contexto";

@Component({
    selector: 'app-meus-plantoes',
    imports: [
        BreadcrumbModule,
        CommonModule,
        FiltrosComponent,
        CardPlantaoComponent,
        PesquisaComponent
    ],
    templateUrl: 'meus-plantoes.html'
})
export class MeusPlantoesComponent implements OnInit {

    readonly plantaoService = inject(PlantaoService);

    // UI
    home = { icon: 'pi pi-home', routerLink: '' };
    items = [{ label: 'Meus Plantões', routerLink: '/meus-plantoes' },];

    // Estado
    plantoes = signal<PlantaoSimple[]>([]);
    loading = signal(false);
    erro = signal<string | null>(null);

    // Controle
    filtros = signal<PlantaoFiltro>({ abertos: false });
    visualizacao = signal<VisualizacaoTipo>(VisualizacaoTipo.LIDER_ESCALA);
    plantaoVisualizacaoContexto = PlantaoVisualizacaoContexto;
    filtroContexto = FiltroContexto;

    ngOnInit() {
        this.carregar();
    }

    // Eventos (vindos do filtro)
    onFiltrosAplicados(filtros: PlantaoFiltro) {

        this.filtros.set({
            ...filtros,
            abertos: this.filtros().abertos ?? false
        });

        this.carregar();
    }

    onVisualizacaoChange(visualizacao: VisualizacaoTipo) {
        this.visualizacao.set(visualizacao);
        this.carregar();
    }

    // Core
    private carregar() {

        this.loading.set(true);
        this.erro.set(null);

        this.plantaoService
            .listarComContexto(this.filtros(), this.visualizacao())
            .subscribe({
                next: (res) => {
                    this.plantoes.set(res);
                    this.loading.set(false);
                },
                error: () => {
                    this.erro.set('Erro ao carregar plantões');
                    this.loading.set(false);
                }
            });
    }

}