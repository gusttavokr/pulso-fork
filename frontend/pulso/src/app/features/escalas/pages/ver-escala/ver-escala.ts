import { Component, computed, inject, OnInit, signal, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { EscalaAPIService } from "../../services/escala";
import { AuthService } from "../../../autenticacao/services/auth";
import { CriacaoPlantaoContextService } from "../../../plantoes/services/criacao-plantao-context";
import { CalendarioEscalaComponent } from "../../components/calendario-escala/calendario-escala";
import { EscalaResponse } from "../../models/escala-response";
import { PlantaoEscalaSimple } from "../../../plantoes/models/plantao-escala-simple";
import { STATUS_ESCALA_CONFIG, StatusEscala } from "../../../../shared/enums/status-escala";
import { BreadcrumbModule } from "primeng/breadcrumb";
import { FeedbackModalComponent } from "../../../../shared/components/modals/feedback-modal/feedback-modal";
import { getEspecialidadeIcon } from "../../../../shared/enums/especialidade-icon";
import { ESPECIALIDADE_LABEL } from "../../../../shared/enums/especialidade";
import { ButtonComponent } from "../../../../shared/components/button/button";

@Component({
    selector: 'app-ver-escala',
    imports: [
        BreadcrumbModule,
        CalendarioEscalaComponent,
        FeedbackModalComponent,
        ButtonComponent
    ],
    templateUrl: 'ver-escala.html'
})
export class VerEscalaComponent implements OnInit {

    private readonly route = inject(ActivatedRoute);
    private readonly escalaService = inject(EscalaAPIService);
    protected readonly authService = inject(AuthService);
    private readonly router = inject(Router);
    private readonly criacaoPlantaoContext = inject(CriacaoPlantaoContextService);

    @ViewChild(CalendarioEscalaComponent)
    calendario!: CalendarioEscalaComponent;

    // Estado
    readonly escala = signal<EscalaResponse | null>(null);
    readonly loading = signal<boolean>(true);

    readonly publishSuccess = signal(false);
    readonly publishError = signal(false);

    // UI
    readonly home = { icon: 'pi pi-home', routerLink: '' };

    readonly items = [
        { label: 'Minhas escalas', routerLink: '/minhas-escalas' },
        {
            label: 'Detalhe escala',
            routerLink: () => {
                const id = this.escala()?.id;
                return id ? `/escala/${id}` : '';
            }
        }
    ];

    // Dados derivados
    // Status
    readonly escalaStatus = computed(() => this.escala()?.status);

    readonly statusConfig = computed(() => {
        const status = this.escalaStatus();
        return status ? STATUS_ESCALA_CONFIG[status] : null;
    });

    readonly isPublicada = computed(() =>
        this.escalaStatus() === StatusEscala.PUBLICADA
    );

    readonly isArquivada = computed(() =>
        this.escalaStatus() === StatusEscala.ARQUIVADA
    );

    // Plantões 
    readonly plantoes = computed(() =>
        this.escala()?.plantoes ?? []
    );

    // Especialidades
    readonly especialidade = computed(() =>
        this.escala()?.especialidadeSetor ?? null
    );

    readonly iconeEspecialidade = computed(() => {
        const especialidade = this.especialidade();
        return especialidade ? getEspecialidadeIcon(especialidade) : '';
    });

    readonly labelEspecialidade = computed(() => {
        const especialidade = this.especialidade();
        return especialidade ? ESPECIALIDADE_LABEL[especialidade] : '';
    });

    // Datas
    private readonly meses = [
        'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];

    readonly dataReferencia = computed(() => {
        const mesReferencia = this.escala()?.mesReferencia;
        return mesReferencia ? new Date(`${mesReferencia}T12:00:00`) : null;
    });

    readonly mesEAno = computed(() => {
        const data = this.dataReferencia();
        if (!data) return '';

        return `${this.meses[data.getMonth()]}, ${data.getFullYear()}`;
    });

    // Ciclo de vida
    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            const id = params.get('id');

            if (id) {
                this.buscarEscala(Number(id));
            }
        });
    }

    // Api
    buscarEscala(id: number): void {
        this.loading.set(true);

        this.escalaService.buscarPorId(id).subscribe({
            next: (escala) => {
                this.escala.set(escala);
                this.loading.set(false);
            },
            error: (error) => {
                console.error('Erro ao buscar escala', error);
                this.loading.set(false);
            }
        });
    }

    publicarEscala(): void {
        const escala = this.escala();
        if (!escala) return;

        this.escalaService.publicar(escala.id).subscribe({
            next: (data) => {
                this.escala.set(data);
                this.publishSuccess.set(true);
            },
            error: () => {
                this.publishError.set(true);
            }
        });
    }

    // Calendário
    previousWeek(): void {
        this.calendario.previousWeek();
    }

    nextWeek(): void {
        this.calendario.nextWeek();
    }

    // Ações
    criarPlantao(): void {
        const escala = this.escala();
        if (!escala) return;

        this.criacaoPlantaoContext.setEscalaId(escala.id);
        this.router.navigate(['/plantao/criar']);
    }

    // Navegação
    goHome(): void {
        this.router.navigate(['/home']);
    }

    goToEscalaDetails(): void {
        const escala = this.escala();
        if (!escala) return;

        this.router.navigate([`/escala/${escala.id}`]);
    }

    // Modais
    closeModal(): void {
        this.publishSuccess.set(false);
        this.publishError.set(false);
    }

    svg1 = `
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path fill="currentColor" d="M22.8109 7.62821L16.3721 1.18946C16.2328 1.05014 16.0675 0.939617 15.8855 0.864214C15.7034 0.78881 15.5084 0.75 15.3114 0.75C15.1143 0.75 14.9193 0.78881 14.7372 0.864214C14.5552 0.939617 14.3899 1.05014 14.2506 1.18946L11.5843 3.85571L6.14682 5.89571C5.89974 5.98765 5.68126 6.14308 5.51338 6.34634C5.3455 6.54961 5.23416 6.79352 5.19057 7.05352L3.24338 18.7348C3.23033 18.8131 3.24248 18.8935 3.27807 18.9645C3.31367 19.0355 3.37087 19.0934 3.44145 19.1297C3.51203 19.1661 3.59234 19.1792 3.6708 19.167C3.74927 19.1548 3.82184 19.118 3.87807 19.062L9.22182 13.7182C9.05251 13.366 8.97849 12.9755 9.00713 12.5857C9.04199 12.1062 9.22953 11.6505 9.54227 11.2853C9.855 10.9201 10.2765 10.6647 10.7449 10.5565C11.2134 10.4484 11.7042 10.4931 12.1454 10.6841C12.5865 10.8752 12.9549 11.2026 13.1965 11.6182C13.4381 12.0339 13.5402 12.516 13.4878 12.994C13.4354 13.4719 13.2314 13.9205 12.9055 14.2739C12.5796 14.6274 12.149 14.8672 11.677 14.9582C11.2049 15.0492 10.716 14.9865 10.2821 14.7795L4.93838 20.1232C4.88231 20.1794 4.84554 20.252 4.83336 20.3305C4.82118 20.4089 4.83422 20.4892 4.87061 20.5598C4.90699 20.6304 4.96484 20.6876 5.03582 20.7232C5.1068 20.7588 5.18725 20.771 5.26557 20.7579L16.9478 18.8107C17.2073 18.7675 17.451 18.6567 17.6542 18.4895C17.8574 18.3223 18.0131 18.1046 18.1056 17.8582L20.1465 12.4141L22.8109 9.75071C22.9502 9.61142 23.0607 9.44604 23.1361 9.26403C23.2115 9.08202 23.2503 8.88694 23.2503 8.68993C23.2503 8.49292 23.2115 8.29784 23.1361 8.11582C23.0607 7.93381 22.9502 7.76844 22.8109 7.62915V7.62821ZM19.5006 10.9395L13.0618 4.50071L15.3118 2.25071L21.7506 8.68946L19.5006 10.9395Z" fill="#020035"/>
        </svg>
    `

    svg2 = `
        <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path fill="currentColor" d="M27 6H22V5C22 4.20435 21.6839 3.44129 21.1213 2.87868C20.5587 2.31607 19.7956 2 19 2H13C12.2044 2 11.4413 2.31607 10.8787 2.87868C10.3161 3.44129 10 4.20435 10 5V6H5C4.73478 6 4.48043 6.10536 4.29289 6.29289C4.10536 6.48043 4 6.73478 4 7C4 7.26522 4.10536 7.51957 4.29289 7.70711C4.48043 7.89464 4.73478 8 5 8H6V26C6 26.5304 6.21071 27.0391 6.58579 27.4142C6.96086 27.7893 7.46957 28 8 28H24C24.5304 28 25.0391 27.7893 25.4142 27.4142C25.7893 27.0391 26 26.5304 26 26V8H27C27.2652 8 27.5196 7.89464 27.7071 7.70711C27.8946 7.51957 28 7.26522 28 7C28 6.73478 27.8946 6.48043 27.7071 6.29289C27.5196 6.10536 27.2652 6 27 6ZM14 21C14 21.2652 13.8946 21.5196 13.7071 21.7071C13.5196 21.8946 13.2652 22 13 22C12.7348 22 12.4804 21.8946 12.2929 21.7071C12.1054 21.5196 12 21.2652 12 21V13C12 12.7348 12.1054 12.4804 12.2929 12.2929C12.4804 12.1054 12.7348 12 13 12C13.2652 12 13.5196 12.1054 13.7071 12.2929C13.8946 12.4804 14 12.7348 14 13V21ZM20 21C20 21.2652 19.8946 21.5196 19.7071 21.7071C19.5196 21.8946 19.2652 22 19 22C18.7348 22 18.4804 21.8946 18.2929 21.7071C18.1054 21.5196 18 21.2652 18 21V13C18 12.7348 18.1054 12.4804 18.2929 12.2929C18.4804 12.1054 18.7348 12 19 12C19.2652 12 19.5196 12.1054 19.7071 12.2929C19.8946 12.4804 20 12.7348 20 13V21ZM20 6H12V5C12 4.73478 12.1054 4.48043 12.2929 4.29289C12.4804 4.10536 12.7348 4 13 4H19C19.2652 4 19.5196 4.10536 19.7071 4.29289C19.8946 4.48043 20 4.73478 20 5V6Z" fill="#FF4141"/>
        </svg>
    `

    svg3 = `
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <g clip-path="url(#clip0_1499_2855)">
        <path d="M21.6937 4.15669C21.6937 4.15669 21.6937 4.16607 21.6937 4.17075L16.2374 22.1651C16.1548 22.4574 15.9848 22.7174 15.7502 22.9103C15.5156 23.1032 15.2276 23.2197 14.9249 23.2442C14.8818 23.2479 14.8387 23.2498 14.7955 23.2498C14.5119 23.2507 14.2338 23.1704 13.9943 23.0184C13.7548 22.8664 13.5637 22.6491 13.4437 22.392L10.0312 15.3889C9.99703 15.3187 9.98563 15.2396 9.99857 15.1626C10.0115 15.0857 10.0481 15.0147 10.1034 14.9595L15.5334 9.5295C15.6681 9.38771 15.7421 9.19889 15.7396 9.00332C15.7371 8.80775 15.6583 8.6209 15.52 8.4826C15.3817 8.34429 15.1948 8.26549 14.9992 8.26299C14.8037 8.26048 14.6148 8.33448 14.473 8.46919L9.04024 13.8992C8.98508 13.9544 8.91407 13.991 8.8371 14.004C8.76013 14.0169 8.68104 14.0055 8.61086 13.9714L1.60117 10.5598C1.32707 10.4283 1.09939 10.2166 0.948314 9.9528C0.797238 9.68897 0.729895 9.38547 0.75521 9.0825C0.780525 8.77954 0.897302 8.49142 1.09007 8.25632C1.28283 8.02123 1.54248 7.85026 1.83461 7.76607L19.829 2.30982H19.843C20.0993 2.23784 20.37 2.23531 20.6275 2.30251C20.885 2.3697 21.12 2.50419 21.3083 2.69217C21.4967 2.88016 21.6317 3.11487 21.6994 3.37224C21.7671 3.6296 21.7651 3.90034 21.6937 4.15669Z" fill="white"/>
        </g>
        <defs>
        <clipPath id="clip0_1499_2855">
        <rect width="24" height="24" fill="white"/>
        </clipPath>
        </defs>
        </svg>
    `

    svg4 = `
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#020035" viewBox="0 0 256 256"><path fill="currentColor" d="M240,208H224V136l2.34,2.34A8,8,0,0,0,237.66,127L139.31,28.68a16,16,0,0,0-22.62,0L18.34,127a8,8,0,0,0,11.32,11.31L32,136v72H16a8,8,0,0,0,0,16H240a8,8,0,0,0,0-16Zm-88,0H104V160a4,4,0,0,1,4-4h40a4,4,0,0,1,4,4Z"></path></svg>
    `

    svg5 = `
        <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M14.4888 13.7466L9.48875 18.7466C9.3489 18.8866 9.17064 18.9819 8.97656 19.0206C8.78247 19.0592 8.58128 19.0394 8.39846 18.9637C8.21564 18.8879 8.0594 18.7596 7.94953 18.595C7.83966 18.4305 7.78109 18.237 7.78125 18.0391V14.0391H-0.21875C-0.483966 14.0391 -0.73832 13.9337 -0.925857 13.7462C-1.11339 13.5586 -1.21875 13.3043 -1.21875 13.0391C-1.21875 12.7738 -1.11339 12.5195 -0.925857 12.332C-0.73832 12.1444 -0.483966 12.0391 -0.21875 12.0391H7.78125V8.03906C7.78109 7.84117 7.83966 7.64767 7.94953 7.48308C8.0594 7.31849 8.21564 7.19019 8.39846 7.11444C8.58128 7.03869 8.78247 7.01888 8.97656 7.05753C9.17064 7.09618 9.3489 7.19155 9.48875 7.33156L14.4888 12.3316C14.5817 12.4244 14.6555 12.5347 14.7058 12.6561C14.7561 12.7775 14.782 12.9076 14.782 13.0391C14.782 13.1705 14.7561 13.3006 14.7058 13.422C14.6555 13.5434 14.5817 13.6537 14.4888 13.7466ZM21.7812 1.03906H13.7812C13.516 1.03906 13.2617 1.14442 13.0741 1.33196C12.8866 1.51949 12.7812 1.77385 12.7812 2.03906C12.7812 2.30428 12.8866 2.55863 13.0741 2.74617C13.2617 2.93371 13.516 3.03906 13.7812 3.03906H20.7812V23.0391H13.7812C13.516 23.0391 13.2617 23.1444 13.0741 23.332C12.8866 23.5195 12.7812 23.7738 12.7812 24.0391C12.7812 24.3043 12.8866 24.5586 13.0741 24.7462C13.2617 24.9337 13.516 25.0391 13.7812 25.0391H21.7812C22.0465 25.0391 22.3008 24.9337 22.4884 24.7462C22.6759 24.5586 22.7812 24.3043 22.7812 24.0391V2.03906C22.7812 1.77385 22.6759 1.51949 22.4884 1.33196C22.3008 1.14442 22.0465 1.03906 21.7812 1.03906Z" fill="white"/>
        </svg>
    `

}