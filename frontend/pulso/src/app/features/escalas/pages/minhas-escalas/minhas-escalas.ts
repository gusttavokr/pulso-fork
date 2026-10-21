import { Component, inject, OnInit, signal } from "@angular/core";
import { AuthService } from "../../../autenticacao/services/auth";
import { Router } from "@angular/router";
import { EscalaAPIService } from "../../services/escala";
import { EscalaFiltro } from "../../models/escala-filtro";
import { EscalaSimple } from "../../models/escala-simple";
import { BreadcrumbModule } from "primeng/breadcrumb";
import { PesquisaComponent } from "../../../../shared/components/pesquisa/pesquisa";
import { ButtonComponent } from "../../../../shared/components/button/button";
import { CardEscalaComponent } from "../../components/card-escala/card-escala";
import { FiltrosComponent } from "../../../../shared/components/filters/filters";
import { FiltroContexto } from "../../../../shared/enums/filtro-contexto";
import { CriarEscalaModalComponent } from "../../components/modal-criar-escala/modal-criar-escala";
import { FeedbackModalComponent } from "../../../../shared/components/modals/feedback-modal/feedback-modal";

@Component({
    selector: 'app-minhas-escalas',
    imports: [
        BreadcrumbModule,
        PesquisaComponent,
        ButtonComponent,
        CardEscalaComponent,
        FiltrosComponent,
        CriarEscalaModalComponent,
        FeedbackModalComponent
    ],
    templateUrl: 'minhas-escalas.html',
})
export class MinhasEscalasComponent implements OnInit {

    private readonly escalaService = inject(EscalaAPIService);
    protected readonly authService = inject(AuthService);
    private readonly router = inject(Router);

    // Breadcrumb
    home = { icon: 'pi pi-home', routerLink: '' };
    items = [{ label: 'Minhas escalas', routerLink: '/minhas-escalas' }];

    // STATE
    escalas = signal<EscalaSimple[]>([]);
    loading = signal(false);
    erro = signal<string | null>(null);

    filtros = signal<EscalaFiltro>({});
    filtroContexto = FiltroContexto;

    // MODAIS
    showCriarModal = signal<boolean>(false);

    escalaSelecionada = signal<EscalaSimple | null>(null);

    // FEEDBACK
    showSuccess = signal(false);
    showError = signal(false);
    idEscalaCriada = signal<number | null>(null);

    // INIT
    ngOnInit(): void {
        this.carregar();
    }

    // CORE
    private carregar(): void {

        this.loading.set(true);
        this.erro.set(null);

        const filtros = this.buildFiltros();

        this.escalaService.listar(filtros).subscribe({
            next: (res) => {
                this.escalas.set(res);
                this.loading.set(false);
            },
            error: () => {
                this.erro.set('Erro ao carregar escalas');
                this.loading.set(false);
            }
        });
    }

    private buildFiltros(): Partial<EscalaFiltro> {

        const f = this.filtros();

        return {
            especialidade: f.especialidade || undefined,
            mesReferencia: f.mesReferencia || undefined,
            status: f.status || undefined,
            liderEscala: this.authService.isLiderEscala || undefined,
            instituicao: this.authService.isInstituicao || undefined,
        };
    }

    // FILTROS
    onFiltrosEscala(filtros: EscalaFiltro): void {

        this.filtros.update(current => ({
            ...current,
            ...filtros
        }));

        this.carregar();
    }

    // CRIAR ESCALA
    abrirCriarModal(): void {
        this.showCriarModal.set(true);
    }

    fecharCriarModal(): void {
        this.showCriarModal.set(false);
    }

    onEscalaCriada(id: number): void {
        this.idEscalaCriada.set(id);
        this.showCriarModal.set(false);
        this.showSuccess.set(true);

        this.carregar();
    }

    onErroCriacao(): void {
        this.showCriarModal.set(false);
        this.showError.set(true);
    }

    // REMOVER ESCALA
    confirmarRemocao(escala: EscalaSimple): void {

        if (!escala) return;

        this.escalaService.remover(Number(escala.id)).subscribe({
            next: () => {
                this.showSuccess.set(true);

                this.escalas.update(list =>
                    list.filter(e => e.id !== escala.id)
                );
            },
            error: () => {
                this.showError.set(true);
            }
        });
    }

    // FEEDBACK
    fecharFeedback(): void {
        this.showSuccess.set(false);
        this.showError.set(false);
    }

    irPraEscala(): void {
        const id = this.idEscalaCriada();
        if (!id) return;

        this.router.navigate(['/escala', id]);
        this.fecharFeedback();
    }

    irParaHome(): void {
        this.fecharFeedback();
        this.router.navigate(['/home']);
    }

    svg = `
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-plus-icon lucide-plus"><path d="M5 12h14"/><path d="M12 5v14"/></svg>
    `

    svg2 = `
    <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M14.4888 13.7466L9.48875 18.7466C9.3489 18.8866 9.17064 18.9819 8.97656 19.0206C8.78247 19.0592 8.58128 19.0394 8.39846 18.9637C8.21564 18.8879 8.0594 18.7596 7.94953 18.595C7.83966 18.4305 7.78109 18.237 7.78125 18.0391V14.0391H-0.21875C-0.483966 14.0391 -0.73832 13.9337 -0.925857 13.7462C-1.11339 13.5586 -1.21875 13.3043 -1.21875 13.0391C-1.21875 12.7738 -1.11339 12.5195 -0.925857 12.332C-0.73832 12.1444 -0.483966 12.0391 -0.21875 12.0391H7.78125V8.03906C7.78109 7.84117 7.83966 7.64767 7.94953 7.48308C8.0594 7.31849 8.21564 7.19019 8.39846 7.11444C8.58128 7.03869 8.78247 7.01888 8.97656 7.05753C9.17064 7.09618 9.3489 7.19155 9.48875 7.33156L14.4888 12.3316C14.5817 12.4244 14.6555 12.5347 14.7058 12.6561C14.7561 12.7775 14.782 12.9076 14.782 13.0391C14.782 13.1705 14.7561 13.3006 14.7058 13.422C14.6555 13.5434 14.5817 13.6537 14.4888 13.7466ZM21.7812 1.03906H13.7812C13.516 1.03906 13.2617 1.14442 13.0741 1.33196C12.8866 1.51949 12.7812 1.77385 12.7812 2.03906C12.7812 2.30428 12.8866 2.55863 13.0741 2.74617C13.2617 2.93371 13.516 3.03906 13.7812 3.03906H20.7812V23.0391H13.7812C13.516 23.0391 13.2617 23.1444 13.0741 23.332C12.8866 23.5195 12.7812 23.7738 12.7812 24.0391C12.7812 24.3043 12.8866 24.5586 13.0741 24.7462C13.2617 24.9337 13.516 25.0391 13.7812 25.0391H21.7812C22.0465 25.0391 22.3008 24.9337 22.4884 24.7462C22.6759 24.5586 22.7812 24.3043 22.7812 24.0391V2.03906C22.7812 1.77385 22.6759 1.51949 22.4884 1.33196C22.3008 1.14442 22.0465 1.03906 21.7812 1.03906Z" fill="white"/>
    </svg>
  `

    svg3 = `
    <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#020035" viewBox="0 0 256 256"><path fill="currentColor" d="M240,208H224V136l2.34,2.34A8,8,0,0,0,237.66,127L139.31,28.68a16,16,0,0,0-22.62,0L18.34,127a8,8,0,0,0,11.32,11.31L32,136v72H16a8,8,0,0,0,0,16H240a8,8,0,0,0,0-16Zm-88,0H104V160a4,4,0,0,1,4-4h40a4,4,0,0,1,4,4Z"></path></svg>
  `
}