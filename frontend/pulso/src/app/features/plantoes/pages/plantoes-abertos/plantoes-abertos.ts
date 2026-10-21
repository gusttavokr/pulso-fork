import { Component, inject, signal } from "@angular/core";
import { PlantaoService } from "../../services/plantao";
import { PlantaoSimple } from "../../models/plantao-simple";
import { PlantaoFiltro } from "../../models/plantao-filtro";
import { VisualizacaoTipo } from "../../../../shared/enums/visualizacao-tipo";
import { PlantaoVisualizacaoContexto } from "../../../../shared/enums/plantao-visualizacao-contexto";
import { FiltroContexto } from "../../../../shared/enums/filtro-contexto";
import { BreadcrumbModule } from "primeng/breadcrumb";
import { PesquisaComponent } from "../../../../shared/components/pesquisa/pesquisa";
import { CardPlantaoComponent } from "../../components/card-plantao/card-plantao";
import { FiltrosComponent } from "../../../../shared/components/filters/filters";
import { CommonModule } from "@angular/common";

@Component({
    selector: 'app-plantoes-abertos',
    imports: [
        BreadcrumbModule,
        CommonModule,
        FiltrosComponent,
        CardPlantaoComponent,
        PesquisaComponent
    ],
    templateUrl: 'plantoes-abertos.html'
})
export class PlantoesAbertosComponent {

    readonly plantaoService = inject(PlantaoService);

    // UI
    home = { icon: 'pi pi-home', routerLink: '' };
    items = [{ label: 'Plantões Abertos', routerLink: '/plantoes-abertos' },];

    // Estado
    plantoes = signal<PlantaoSimple[]>([]);
    loading = signal(false);
    erro = signal<string | null>(null);

    // Controle
    filtros = signal<PlantaoFiltro>({ abertos: true });
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
            abertos: this.filtros().abertos ?? true
        });

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