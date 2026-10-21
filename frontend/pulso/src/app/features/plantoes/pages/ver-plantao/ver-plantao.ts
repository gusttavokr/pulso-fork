import { Component, computed, inject, OnInit, signal } from "@angular/core";
import { PlantaoService } from "../../services/plantao";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../../../autenticacao/services/auth";
import { DetalhePlantaoContextService } from "../../services/detalhe-plantao-context";
import { PlantaoResponse } from "../../models/plantao-response";
import { DomSanitizer } from "@angular/platform-browser";
import { AlocacaoRequest } from "../../models/alocacao-request";
import { TipoTroca } from "../../../../shared/enums/tipo-troca";
import { StatusEscala } from "../../../../shared/enums/status-escala";
import { calcularDuracaoHoras, calcularStatusTempo, escalaPublicada, isMeusPlantoes, isPlantaoAberto, obterCorAlerta } from "../../utils/plantao";
import { BreadcrumbModule } from "primeng/breadcrumb";
import { ESPECIALIDADE_LABEL } from "../../../../shared/enums/especialidade";
import { getEspecialidadeIcon } from "../../../../shared/enums/especialidade-icon";
import { CommonModule } from "@angular/common";
import { TagModule } from "primeng/tag";
import { TextareaModule } from "primeng/textarea";
import { FormsModule } from "@angular/forms";
import { FormaPagamento, FormaPagamentoLabel } from "../../../../shared/enums/forma-pagamento";
import { ButtonComponent } from "../../../../shared/components/button/button";
import { ModalAlocarProfissionaisComponent } from "../../components/modal-alocar-profissionais/modal-alocar-profissionais";
import { FeedbackModalComponent } from "../../../../shared/components/modals/feedback-modal/feedback-modal";

@Component({
    selector: 'app-ver-plantao',
    imports: [
        BreadcrumbModule,
        CommonModule,
        TagModule,
        TextareaModule,
        FormsModule,
        ButtonComponent,
        ModalAlocarProfissionaisComponent,
        FeedbackModalComponent
    ],
    templateUrl: 'ver-plantao.html'
})
export class VerPlantaoComponent implements OnInit {

    private readonly plantaoService = inject(PlantaoService);
    private readonly route = inject(ActivatedRoute);
    private readonly router = inject(Router);
    protected readonly authService = inject(AuthService);
    private readonly detalheContext = inject(DetalhePlantaoContextService);
    private readonly sanitizer = inject(DomSanitizer);

    // State
    plantao = signal<PlantaoResponse | null>(null);

    showConfirm = signal(false);

    alocacaoSucesso = signal(false);
    alocacaoErro = signal(false);

    pegouSucesso = signal(false);
    pegouErro = signal(false);

    comentario = signal('');
    comentarioErro = signal(false);

    // Route
    idPlantao = Number(this.route.snapshot.paramMap.get('id'));

    // Breadcrumb
    home = { icon: 'pi pi-home', routerLink: '' };
    items = [
        { label: `Meus plantões`, routerLink: `/meus-plantoes` },
        { label: `Detalhe plantão`, routerLink: `/plantao/${this.idPlantao}` }
    ];

    // Computed
    readonly statusTempo = computed(() => {
        const p = this.plantao();
        if (!p) return 'distante';
        return calcularStatusTempo(p.horaEntrada);
    });

    readonly corAlerta = computed(() =>
        obterCorAlerta(this.statusTempo())
    );

    readonly duracaoHoras = computed(() => {
        const p = this.plantao();
        if (!p) return 0;
        return calcularDuracaoHoras(p.horaEntrada, p.horaSaida);
    });

    readonly statusPlantao = computed(() => this.getStatusPlantao());

    readonly limiteAtingido = computed(() => {
        const p = this.plantao();
        if (!p) return false;

        return (
            p.profissionaisConfirmados.length +
            p.profissionaisCombinados.length
        ) >= p.quantidadeProfissionais;
    });

    readonly isEscalaPublicada = computed(() => {
        const p = this.plantao();
        return p ? escalaPublicada(p.statusEscala) : false;
    });

    readonly labelEspecialidade = computed(() => {
        const p = this.plantao();
        if (!p) return '';

        return (
            ESPECIALIDADE_LABEL[p.especialidade] ??
            p.especialidade
        );
    });

    readonly iconeEspecialidade = computed(() => {
        const p = this.plantao();
        if (!p) return '';

        const svg = getEspecialidadeIcon(p.especialidade);

        return this.sanitizer.bypassSecurityTrustHtml(svg);
    });

    readonly labelFormaPagamento = computed(() => {
        const p = this.plantao();

        if (!p?.formaPagamento) return '';

        return FormaPagamentoLabel[p.formaPagamento as FormaPagamento];
    });

    readonly quantidadeProfissionaisAlocados = computed(() => {
        const p = this.plantao();

        if (!p) return 0;

        return (
            p.profissionaisCombinados.length +
            p.profissionaisConfirmados.length +
            p.profissionaisTrocaDestino.length
        );
    });

    // Lifecycle
    ngOnInit() {
        if (this.idPlantao) {
            this.buscarPlantao(this.idPlantao);
        }
    }

    // API
    private buscarPlantao(id: number): void {
        this.plantaoService.buscarPorId(id).subscribe({
            next: (data: any) => {
                const plantao = data;
                this.plantao.set(plantao);
            },
            error: (error: any) => {
                console.log('erro ao buscar plantao', error);
            }
        })
    }

    pegarPlantao(): void {
        const payload: AlocacaoRequest = {
            idPlantao: this.plantao()?.id
        }

        this.plantaoService.alocar(payload).subscribe({
            next: (data: any) => {
                const plantao = data;
                this.plantao.set(plantao);
                this.pegouSucesso.set(true);
            },
            error: (error: any) => {
                console.log('erro ao pegar plantao', error);
                this.pegouErro.set(true);
            }
        })
    }

    salvarComentario(event: Event) {
        const keyboardEvent = event as KeyboardEvent;
        keyboardEvent.preventDefault();

        const texto = this.comentario()?.trim();

        if (!texto || texto.length < 3) {
            this.comentarioErro.set(true);
            return;
        }

        const plantaoId = this.plantao()?.id;
        if (!plantaoId) return;

        this.plantaoService
            .atualizarComentario(plantaoId, texto)
            .subscribe({
                next: (plantaoAtualizado) => {
                    this.plantao.set(plantaoAtualizado);
                    this.comentario.set('');
                },
                error: err => console.error(err)
            });
    }

    onDescombinar(profissionalId: number) {
        const req: AlocacaoRequest = {
            idPlantao: this.plantao()?.id,
            idProfissional: profissionalId
        };

        this.plantaoService.atualizarCombinacao(req, false).subscribe({
            next: (plantaoAtualizado) => {
                this.plantao.set(plantaoAtualizado);
            },
            error: (error: any) => {
                console.log("erro ao descominar profissional do plantao", error);
            }
        });
    }

    // Regras de negócio
    private getStatusPlantao(): string {
        const plantao = this.plantao();
        if (!plantao) return '';

        const {
            profissionaisCombinados,
            profissionaisConfirmados,
            profissionaisTrocaDestino,
            tipoTroca
        } = plantao;

        const todasListasVazias =
            profissionaisCombinados.length === 0 &&
            profissionaisConfirmados.length === 0 &&
            profissionaisTrocaDestino.length === 0;

        if (todasListasVazias) {
            return 'Novo plantão!';
        }

        if (tipoTroca === TipoTroca.DUPLA) {
            return 'Troca';
        }

        if (tipoTroca === TipoTroca.SIMPLES) {
            return 'Repasse';
        }

        return '';
    }

    isUsuarioRelacionadoAoPlantao(): boolean {
        const user = this.authService.user;

        if (!user?.profissional?.id) {
            return false;
        }

        const profissionalId = user.profissional.id;

        const profissionais = [
            ...(this.plantao()?.profissionaisCombinados ?? []),
            ...(this.plantao()?.profissionaisConfirmados ?? []),
            ...(this.plantao()?.profissionaisTrocaDestino ?? [])
        ];

        return profissionais.some(p => p.id === profissionalId);
    }

    podeDescombinarProfissional(idProfissional: number): boolean {

        const user = this.authService.user;

        if (!user) return false;

        if (this.plantao()!.statusEscala !== StatusEscala.RASCUNHO) {
            return false;
        }

        if (user.profissional!.id !== this.plantao()!.idLider) {
            return false;
        }

        const profissionalConfirmado = this.plantao()!.profissionaisCombinados
            .some(p => p.id === idProfissional);

        if (!profissionalConfirmado) {
            return false;
        }

        return true;
    }

    podeAtualizar(): boolean {
        const p = this.plantao();

        if (!p) return false;

        return this.authService.isLiderEscala &&
            p.statusEscala !== StatusEscala.PUBLICADA &&
            p.idLider === this.authService.user?.profissional?.id;
    }

    readonly listaProfissionais = computed(() => {
        const plantao = this.plantao();
        if (!plantao) return [];

        const combinados = plantao.profissionaisCombinados.map(p => ({
            ...p,
            status: 'Combinado' as const
        }));

        const alocados = plantao.profissionaisConfirmados.map(p => ({
            ...p,
            status: 'Alocado' as const
        }));

        const trocas = plantao.profissionaisTrocaDestino.map(p => ({
            ...p,
            status: 'Troca' as const
        }));

        return [
            ...combinados,
            ...alocados,
            ...trocas
        ];
    });

    // Contexto
    readonly isPlantaoAberto = computed(() =>
        isPlantaoAberto(this.detalheContext.context)
    );

    readonly isMeusPlantoes = computed(() =>
        isMeusPlantoes(this.detalheContext.context)
    );

    // Modais
    showConfirmDialog() {
        if (this.limiteAtingido() || this.isEscalaPublicada()) return;
        this.showConfirm.set(true);
    }

    fecharAlocacaoModal() {
        this.alocacaoSucesso.set(false);
        this.alocacaoErro.set(false);
    }

    fecharModalPegou() {
        this.pegouSucesso.set(false);
        this.pegouErro.set(false);
    }

    refresh() {
        this.showConfirm.set(false);
        this.alocacaoSucesso.set(true);

        const p = this.plantao();
        if (p?.id) {
            this.buscarPlantao(p.id);
        }
    }

    // Navigation
    irParaEscala(): void {
        const idEscala = this.plantao()?.idEscala;

        if (!idEscala) return;

        this.router.navigate(['/escala/', idEscala]);
    }

    abrirDialogEditarPlantao() {
        this.router.navigate(['/plantao/', this.plantao()!.id, 'atualizar']);
    }

    irParaHome() {
        this.router.navigate(['/home']);
    }

    svg = `
        <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
        <g clip-path="url(#clip0_1540_4878)">
        <path d="M14.4888 13.7485L9.48875 18.7485C9.3489 18.8885 9.17064 18.9839 8.97656 19.0225C8.78247 19.0612 8.58128 19.0414 8.39846 18.9656C8.21564 18.8899 8.0594 18.7616 7.94953 18.597C7.83966 18.4324 7.78109 18.2389 7.78125 18.041V14.041H-0.21875C-0.483966 14.041 -0.73832 13.9357 -0.925857 13.7481C-1.11339 13.5606 -1.21875 13.3062 -1.21875 13.041C-1.21875 12.7758 -1.11339 12.5214 -0.925857 12.3339C-0.73832 12.1464 -0.483966 12.041 -0.21875 12.041H7.78125V8.04102C7.78109 7.84312 7.83966 7.64963 7.94953 7.48503C8.0594 7.32044 8.21564 7.19215 8.39846 7.11639C8.58128 7.04064 8.78247 7.02084 8.97656 7.05949C9.17064 7.09814 9.3489 7.1935 9.48875 7.33352L14.4888 12.3335C14.5817 12.4264 14.6555 12.5367 14.7058 12.6581C14.7561 12.7795 14.782 12.9096 14.782 13.041C14.782 13.1724 14.7561 13.3026 14.7058 13.424C14.6555 13.5454 14.5817 13.6556 14.4888 13.7485ZM21.7812 1.04102H13.7812C13.516 1.04102 13.2617 1.14637 13.0741 1.33391C12.8866 1.52145 12.7812 1.7758 12.7812 2.04102C12.7812 2.30623 12.8866 2.56059 13.0741 2.74812C13.2617 2.93566 13.516 3.04102 13.7812 3.04102H20.7812V23.041H13.7812C13.516 23.041 13.2617 23.1464 13.0741 23.3339C12.8866 23.5214 12.7812 23.7758 12.7812 24.041C12.7812 24.3062 12.8866 24.5606 13.0741 24.7481C13.2617 24.9357 13.516 25.041 13.7812 25.041H21.7812C22.0465 25.041 22.3008 24.9357 22.4884 24.7481C22.6759 24.5606 22.7812 24.3062 22.7812 24.041V2.04102C22.7812 1.7758 22.6759 1.52145 22.4884 1.33391C22.3008 1.14637 22.0465 1.04102 21.7812 1.04102Z" fill="white"/>
        </g>
        <defs>
        <clipPath id="clip0_1540_4878">
        <rect width="25.6" height="25.6" fill="white"/>
        </clipPath>
        </defs>
        </svg>
    `

    svg2 = `
        <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
        <g clip-path="url(#clip0_1540_4878)">
        <path d="M14.4888 13.7485L9.48875 18.7485C9.3489 18.8885 9.17064 18.9839 8.97656 19.0225C8.78247 19.0612 8.58128 19.0414 8.39846 18.9656C8.21564 18.8899 8.0594 18.7616 7.94953 18.597C7.83966 18.4324 7.78109 18.2389 7.78125 18.041V14.041H-0.21875C-0.483966 14.041 -0.73832 13.9357 -0.925857 13.7481C-1.11339 13.5606 -1.21875 13.3062 -1.21875 13.041C-1.21875 12.7758 -1.11339 12.5214 -0.925857 12.3339C-0.73832 12.1464 -0.483966 12.041 -0.21875 12.041H7.78125V8.04102C7.78109 7.84312 7.83966 7.64963 7.94953 7.48503C8.0594 7.32044 8.21564 7.19215 8.39846 7.11639C8.58128 7.04064 8.78247 7.02084 8.97656 7.05949C9.17064 7.09814 9.3489 7.1935 9.48875 7.33352L14.4888 12.3335C14.5817 12.4264 14.6555 12.5367 14.7058 12.6581C14.7561 12.7795 14.782 12.9096 14.782 13.041C14.782 13.1724 14.7561 13.3026 14.7058 13.424C14.6555 13.5454 14.5817 13.6556 14.4888 13.7485ZM21.7812 1.04102H13.7812C13.516 1.04102 13.2617 1.14637 13.0741 1.33391C12.8866 1.52145 12.7812 1.7758 12.7812 2.04102C12.7812 2.30623 12.8866 2.56059 13.0741 2.74812C13.2617 2.93566 13.516 3.04102 13.7812 3.04102H20.7812V23.041H13.7812C13.516 23.041 13.2617 23.1464 13.0741 23.3339C12.8866 23.5214 12.7812 23.7758 12.7812 24.041C12.7812 24.3062 12.8866 24.5606 13.0741 24.7481C13.2617 24.9357 13.516 25.041 13.7812 25.041H21.7812C22.0465 25.041 22.3008 24.9357 22.4884 24.7481C22.6759 24.5606 22.7812 24.3062 22.7812 24.041V2.04102C22.7812 1.7758 22.6759 1.52145 22.4884 1.33391C22.3008 1.14637 22.0465 1.04102 21.7812 1.04102Z" fill="#848484"/>
        </g>
        <defs>
        <clipPath id="clip0_1540_4878">
        <rect width="25.6" height="25.6" fill="white"/>
        </clipPath>
        </defs>
        </svg>
    `

    svg3 = `
        <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M14.4888 13.7466L9.48875 18.7466C9.3489 18.8866 9.17064 18.9819 8.97656 19.0206C8.78247 19.0592 8.58128 19.0394 8.39846 18.9637C8.21564 18.8879 8.0594 18.7596 7.94953 18.595C7.83966 18.4305 7.78109 18.237 7.78125 18.0391V14.0391H-0.21875C-0.483966 14.0391 -0.73832 13.9337 -0.925857 13.7462C-1.11339 13.5586 -1.21875 13.3043 -1.21875 13.0391C-1.21875 12.7738 -1.11339 12.5195 -0.925857 12.332C-0.73832 12.1444 -0.483966 12.0391 -0.21875 12.0391H7.78125V8.03906C7.78109 7.84117 7.83966 7.64767 7.94953 7.48308C8.0594 7.31849 8.21564 7.19019 8.39846 7.11444C8.58128 7.03869 8.78247 7.01888 8.97656 7.05753C9.17064 7.09618 9.3489 7.19155 9.48875 7.33156L14.4888 12.3316C14.5817 12.4244 14.6555 12.5347 14.7058 12.6561C14.7561 12.7775 14.782 12.9076 14.782 13.0391C14.782 13.1705 14.7561 13.3006 14.7058 13.422C14.6555 13.5434 14.5817 13.6537 14.4888 13.7466ZM21.7812 1.03906H13.7812C13.516 1.03906 13.2617 1.14442 13.0741 1.33196C12.8866 1.51949 12.7812 1.77385 12.7812 2.03906C12.7812 2.30428 12.8866 2.55863 13.0741 2.74617C13.2617 2.93371 13.516 3.03906 13.7812 3.03906H20.7812V23.0391H13.7812C13.516 23.0391 13.2617 23.1444 13.0741 23.332C12.8866 23.5195 12.7812 23.7738 12.7812 24.0391C12.7812 24.3043 12.8866 24.5586 13.0741 24.7462C13.2617 24.9337 13.516 25.0391 13.7812 25.0391H21.7812C22.0465 25.0391 22.3008 24.9337 22.4884 24.7462C22.6759 24.5586 22.7812 24.3043 22.7812 24.0391V2.03906C22.7812 1.77385 22.6759 1.51949 22.4884 1.33196C22.3008 1.14442 22.0465 1.03906 21.7812 1.03906Z" fill="white"/>
        </svg>
    `

    svg4 = `
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M22.8109 7.62821L16.3721 1.18946C16.2328 1.05014 16.0675 0.939617 15.8855 0.864214C15.7034 0.78881 15.5084 0.75 15.3114 0.75C15.1143 0.75 14.9193 0.78881 14.7372 0.864214C14.5552 0.939617 14.3899 1.05014 14.2506 1.18946L11.5843 3.85571L6.14682 5.89571C5.89974 5.98765 5.68126 6.14308 5.51338 6.34634C5.3455 6.54961 5.23416 6.79352 5.19057 7.05352L3.24338 18.7348C3.23033 18.8131 3.24248 18.8935 3.27807 18.9645C3.31367 19.0355 3.37087 19.0934 3.44145 19.1297C3.51203 19.1661 3.59234 19.1792 3.6708 19.167C3.74927 19.1548 3.82184 19.118 3.87807 19.062L9.22182 13.7182C9.05251 13.366 8.97849 12.9755 9.00713 12.5857C9.04199 12.1062 9.22953 11.6505 9.54227 11.2853C9.855 10.9201 10.2765 10.6647 10.7449 10.5565C11.2134 10.4484 11.7042 10.4931 12.1454 10.6841C12.5865 10.8752 12.9549 11.2026 13.1965 11.6182C13.4381 12.0339 13.5402 12.516 13.4878 12.994C13.4354 13.4719 13.2314 13.9205 12.9055 14.2739C12.5796 14.6274 12.149 14.8672 11.677 14.9582C11.2049 15.0492 10.716 14.9865 10.2821 14.7795L4.93838 20.1232C4.88231 20.1794 4.84554 20.252 4.83336 20.3305C4.82118 20.4089 4.83422 20.4893 4.87061 20.5598C4.90699 20.6304 4.96484 20.6876 5.03582 20.7232C5.1068 20.7588 5.18725 20.771 5.26557 20.7579L16.9478 18.8107C17.2073 18.7675 17.451 18.6567 17.6542 18.4895C17.8574 18.3223 18.0131 18.1046 18.1056 17.8582L20.1465 12.4141L22.8109 9.75071C22.9502 9.61142 23.0607 9.44605 23.1361 9.26403C23.2115 9.08202 23.2503 8.88694 23.2503 8.68993C23.2503 8.49292 23.2115 8.29784 23.1361 8.11583C23.0607 7.93381 22.9502 7.7675 22.8109 7.62821ZM19.5006 10.9395L13.0618 4.50071L15.3118 2.25071L21.7506 8.68946L19.5006 10.9395Z" fill="#020035"/>
        </svg>
    `

}