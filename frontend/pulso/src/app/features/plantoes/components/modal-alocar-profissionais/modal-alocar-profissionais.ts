import { Component, computed, EventEmitter, inject, Input, OnInit, Output, PLATFORM_ID, signal } from "@angular/core";
import { ProfissionalAPIService } from "../../../profissionais/services/profissionalAPI";
import { PlantaoService } from "../../services/plantao";
import { EstatisticaPlantoesAno, ProfissionalEstatistica } from "../../../profissionais/models/profissional-estatistica";
import { AlocacaoRequest } from "../../models/alocacao-request";
import { PesquisaComponent } from "../../../../shared/components/pesquisa/pesquisa";
import { ButtonComponent } from "../../../../shared/components/button/button";
import { CommonModule } from "@angular/common";
import { TagModule } from "primeng/tag";
import { ChartModule } from 'primeng/chart';
import { ModalComponent } from "../../../../shared/components/modal/modal";

@Component({
    selector: 'app-modal-alocar-profissionais',
    imports: [
        PesquisaComponent,
        ButtonComponent,
        CommonModule,
        TagModule,
        ChartModule,
        ModalComponent
    ],
    templateUrl: 'modal-alocar-profissionais.html'
})
export class ModalAlocarProfissionaisComponent implements OnInit {

    private readonly profissionalService = inject(ProfissionalAPIService);
    private readonly plantaoService = inject(PlantaoService);
    private readonly platformId = inject(PLATFORM_ID);

    @Input({ required: true }) idPlantao!: number;
    @Input({ required: true }) quantidadeProfissionais!: number;
    @Input({ required: true }) quantidadeAlocados!: number;

    @Output() closed = new EventEmitter<void>();
    @Output() alocado = new EventEmitter<void>();
    @Output() erroAoAlocar = new EventEmitter<string>();

    // State
    profissionais = signal<ProfissionalEstatistica[]>([]);
    profissionalSelecionado = signal<ProfissionalEstatistica | null>(null);
    profissionaisAlocados = signal<ProfissionalEstatistica[]>([]);

    data: any;
    options: any;

    // Computeds
    totalAlocadosComputed = computed(() =>
        this.quantidadeAlocados + this.profissionaisAlocados().length
    );

    progresso = computed(() => {
        if (!this.quantidadeProfissionais) return 0;

        return (this.totalAlocadosComputed() / this.quantidadeProfissionais) * 100;
    });

    podeAlocar = computed(() =>
        this.totalAlocadosComputed() < this.quantidadeProfissionais
    );

    // Constantes
    private readonly MESES_LABEL = [
        'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
        'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'
    ];

    // Lifecycle
    ngOnInit() {
        this.buscarProfissionais();
    }

    // Data loading
    private buscarProfissionais() {
        this.profissionalService.listar(this.idPlantao).subscribe({
            next: (profissionais) => this.profissionais.set(profissionais),
            error: (erro) =>
                console.error('Erro ao carregar profissionais:', erro)
        });
    }

    // Seleção
    selecionarProfissional(profissional: ProfissionalEstatistica) {
        this.profissionalSelecionado.set(profissional);
        this.initChart(profissional.estatisticasPlantoes);
    }

    isProfissionalAlocado(profissional: ProfissionalEstatistica): boolean {
        return this.profissionaisAlocados().some(p => p.id === profissional.id);
    }

    getClasseBorda(profissional: ProfissionalEstatistica): string {
        return this.profissionalSelecionado()?.id === profissional.id
            ? 'border-2 border-[#2D69FF] shadow-lg'
            : '';
    }

    // Alocação
    alocar() {
        const profissional = this.profissionalSelecionado();

        if (!profissional || !this.podeAlocar()) return;

        const jaSelecionado = this.isProfissionalAlocado(profissional);
        if (jaSelecionado) return;

        this.profissionaisAlocados.update(lista => [...lista, profissional]);
    }

    desalocar() {
        const profissional = this.profissionalSelecionado();
        if (!profissional) return;

        this.profissionaisAlocados.update(lista =>
            lista.filter(p => p.id !== profissional.id)
        );
    }

    // Confirmação
    confirmarAlocacao() {
        const profissionais = this.profissionaisAlocados();

        if (!profissionais.length) {
            alert('Selecione ao menos um profissional.');
            return;
        }

        profissionais.forEach((profissional) => {
            const req: AlocacaoRequest = {
                idPlantao: this.idPlantao,
                idProfissional: profissional.id
            };

            this.plantaoService
                .atualizarCombinacao(req, true)
                .subscribe({
                    next: () => this.alocado.emit(),
                    error: () => this.erroAoAlocar.emit()
                });
        });
    }

    // Chart
    private initChart(estatistica: EstatisticaPlantoesAno) {
        const documentStyle = getComputedStyle(document.documentElement);

        const dadosPorMes = this.mapearDadosGrafico(estatistica);

        this.data = {
            labels: this.MESES_LABEL,
            datasets: [
                {
                    label: `Plantões em ${estatistica.ano}`,
                    data: dadosPorMes,
                    fill: false,
                    tension: 0.4,
                    borderColor: '#21C694',
                    backgroundColor: '#21C694',
                    pointRadius: 5,
                    pointHoverRadius: 7
                }
            ]
        };

        this.options = {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    labels: {
                        color: documentStyle.getPropertyValue('--p-text-color')
                    }
                }
            }
        };
    }

    private mapearDadosGrafico(dto: EstatisticaPlantoesAno): number[] {
        const dados = Array(12).fill(0);

        dto.meses.forEach(item => {
            dados[item.mes - 1] = item.quantidade;
        });

        return dados;
    }

    // UI
    close(): void {
        this.closed.emit();
    }

    svg1 = `
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#020035" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-search-icon lucide-search"><path stroke="currentColor" d="m21 21-4.34-4.34" /><circle stroke="currentColor" cx="11" cy="11" r="8"/></svg>
    `

    svg2 = `
        <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M14.4888 13.7485L9.48875 18.7485C9.3489 18.8885 9.17064 18.9839 8.97656 19.0225C8.78247 19.0612 8.58128 19.0414 8.39846 18.9656C8.21564 18.8899 8.0594 18.7616 7.94953 18.597C7.83966 18.4324 7.78109 18.2389 7.78125 18.041V14.041H-0.21875C-0.483966 14.041 -0.73832 13.9357 -0.925857 13.7481C-1.11339 13.5606 -1.21875 13.3062 -1.21875 13.041C-1.21875 12.7758 -1.11339 12.5214 -0.925857 12.3339C-0.73832 12.1464 -0.483966 12.041 -0.21875 12.041H7.78125V8.04102C7.78109 7.84312 7.83966 7.64963 7.94953 7.48503C8.0594 7.32044 8.21564 7.19215 8.39846 7.11639C8.58128 7.04064 8.78247 7.02084 8.97656 7.05949C9.17064 7.09814 9.3489 7.1935 9.48875 7.33352L14.4888 12.3335C14.5817 12.4264 14.6555 12.5367 14.7058 12.6581C14.7561 12.7795 14.782 12.9096 14.782 13.041C14.782 13.1724 14.7561 13.3026 14.7058 13.424C14.6555 13.5454 14.5817 13.6556 14.4888 13.7485ZM21.7812 1.04102H13.7812C13.516 1.04102 13.2617 1.14637 13.0741 1.33391C12.8866 1.52145 12.7812 1.7758 12.7812 2.04102C12.7812 2.30623 12.8866 2.56059 13.0741 2.74812C13.2617 2.93566 13.516 3.04102 13.7812 3.04102H20.7812V23.041H13.7812C13.516 23.041 13.2617 23.1464 13.0741 23.3339C12.8866 23.5214 12.7812 23.7758 12.7812 24.041C12.7812 24.3062 12.8866 24.5606 13.0741 24.7481C13.2617 24.9357 13.516 25.041 13.7812 25.041H21.7812C22.0465 25.041 22.3008 24.9357 22.4884 24.7481C22.6759 24.5606 22.7812 24.3062 22.7812 24.041V2.04102C22.7812 1.7758 22.6759 1.52145 22.4884 1.33391C22.3008 1.14637 22.0465 1.04102 21.7812 1.04102Z" fill="white"/>
        </svg>
    `

    svg3 = `
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#ffffff" viewBox="0 0 256 256"><path d="M256,136a8,8,0,0,1-8,8H232v16a8,8,0,0,1-16,0V144H200a8,8,0,0,1,0-16h16V112a8,8,0,0,1,16,0v16h16A8,8,0,0,1,256,136ZM144,157.68a68,68,0,1,0-71.9,0c-20.65,6.76-39.23,19.39-54.17,37.17A8,8,0,0,0,24,208H192a8,8,0,0,0,6.13-13.15C183.18,177.07,164.6,164.44,144,157.68Z"></path></svg>
    `

    svg4 = `
            <svg width="32" height="32" viewBox="0 0 32 32" fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path fill="currentColor"
                    d="M27 6H22V5C22 4.20435 21.6839 3.44129 21.1213 2.87868C20.5587 2.31607 19.7956 2 19 2H13C12.2044 2 11.4413 2.31607 10.8787 2.87868C10.3161 3.44129 10 4.20435 10 5V6H5C4.73478 6 4.48043 6.10536 4.29289 6.29289C4.10536 6.48043 4 6.73478 4 7C4 7.26522 4.10536 7.51957 4.29289 7.70711C4.48043 7.89464 4.73478 8 5 8H6V26C6 26.5304 6.21071 27.0391 6.58579 27.4142C6.96086 27.7893 7.46957 28 8 28H24C24.5304 28 25.0391 27.7893 25.4142 27.4142C25.7893 27.0391 26 26.5304 26 26V8H27C27.2652 8 27.5196 7.89464 27.7071 7.70711C27.8946 7.51957 28 7.26522 28 7C28 6.73478 27.8946 6.48043 27.7071 6.29289C27.5196 6.10536 27.2652 6 27 6ZM14 21C14 21.2652 13.8946 21.5196 13.7071 21.7071C13.5196 21.8946 13.2652 22 13 22C12.7348 22 12.4804 21.8946 12.2929 21.7071C12.1054 21.5196 12 21.2652 12 21V13C12 12.7348 12.1054 12.4804 12.2929 12.2929C12.4804 12.1054 12.7348 12 13 12C13.2652 12 13.5196 12.1054 13.7071 12.2929C13.8946 12.4804 14 12.7348 14 13V21ZM20 21C20 21.2652 19.8946 21.5196 19.7071 21.7071C19.5196 21.8946 19.2652 22 19 22C18.7348 22 18.4804 21.8946 18.2929 21.7071C18.1054 21.5196 18 21.2652 18 21V13C18 12.7348 18.1054 12.4804 18.2929 12.2929C18.4804 12.1054 18.7348 12 19 12C19.2652 12 19.5196 12.1054 19.7071 12.2929C19.8946 12.4804 20 12.7348 20 13V21ZM20 6H12V5C12 4.73478 12.1054 4.48043 12.2929 4.29289C12.4804 4.10536 12.7348 4 13 4H19C19.2652 4 19.5196 4.10536 19.7071 4.29289C19.8946 4.48043 20 4.73478 20 5V6Z"
                    fill="#020035" />
            </svg>
        `

}