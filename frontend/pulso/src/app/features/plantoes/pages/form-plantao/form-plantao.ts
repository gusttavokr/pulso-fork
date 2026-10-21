import { Component, inject, OnInit, signal } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { CriacaoPlantaoContextService } from "../../services/criacao-plantao-context";
import { PlantaoResponse } from "../../models/plantao-response";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { PlantaoRequest } from "../../models/plantao-request";
import { PlantaoService } from "../../services/plantao";
import { dataEncerramentoDepoisInicioValidator, futureOrTodayDateValidator, horaFinalDepoisInicialValidator, validTimeValidator } from "../../validators/form-plantao";
import { FormaPagamento, FormaPagamentoLabel } from "../../../../shared/enums/forma-pagamento";
import { BreadcrumbModule } from "primeng/breadcrumb";
import { DatePickerModule } from "primeng/datepicker";
import { ButtonComponent } from "../../../../shared/components/button/button";
import { SelectModule } from "primeng/select";
import { InputNumberModule } from "primeng/inputnumber";
import { FeedbackModalComponent } from "../../../../shared/components/modals/feedback-modal/feedback-modal";
import { TextareaModule } from 'primeng/textarea';

@Component({
    selector: 'app-form-plantao',
    imports: [
        BreadcrumbModule,
        ReactiveFormsModule,
        DatePickerModule,
        ButtonComponent,
        SelectModule,
        InputNumberModule,
        FeedbackModalComponent,
        TextareaModule,
    ],
    templateUrl: 'form-plantao.html'
})
export class FormPlantaoComponent implements OnInit {

    private readonly route = inject(ActivatedRoute);
    private readonly service = inject(PlantaoService);
    private readonly router = inject(Router);
    private readonly context = inject(CriacaoPlantaoContextService);

    // State
    plantaoId = signal<number | null>(null);
    escalaId = signal<number | null>(null);
    isEdicao = signal(false);

    plantao = signal<PlantaoResponse | null>(null);

    step = signal<1 | 2>(1);
    errorMessage = signal<string | null>(null);

    sucesso = signal(false);
    erro = signal(false);

    // Breadcrumb
    home = { icon: 'pi pi-home', routerLink: '' };
    items = [
        { label: `Meus plantões`, routerLink: `/meus-plantoes` },
        { label: `Detalhe plantão`, routerLink: `/plantao/${this.plantaoId()}` },
        { label: 'Atualizar', routerlink: `/plantao/${this.plantaoId()}/atualizar` }
    ];

    // Form 1
    form = new FormGroup({
        dataInicio: new FormControl<Date | null>(null, [
            Validators.required,
            futureOrTodayDateValidator
        ]),
        dataEncerramento: new FormControl<Date | null>(null, [
            Validators.required,
            futureOrTodayDateValidator
        ]),
        horaInicio: new FormControl<Date | null>(null, [
            Validators.required,
            validTimeValidator
        ]),
        horaEncerramento: new FormControl<Date | null>(null, [
            Validators.required,
            validTimeValidator
        ]),
        comentario: new FormControl<string | null>(null, [
            Validators.minLength(3)
        ]),
    }, {
        validators: [
            horaFinalDepoisInicialValidator,
            dataEncerramentoDepoisInicioValidator
        ]
    });

    // Form 2
    form2 = new FormGroup({
        quantidadeProfissionais: new FormControl<number | null>(null, [
            Validators.required,
            Validators.min(1)
        ]),
        formaPagamento: new FormControl<FormaPagamento | null>(null),
        valor: new FormControl<number | null>(null, [
            Validators.min(0)
        ]),
        estimativaPagamento: new FormControl<Date | null>(null, [
            futureOrTodayDateValidator
        ]),
    });

    // Lists e enums
    formasPagamento = Object.entries(FormaPagamentoLabel).map(
        ([value, label]) => ({
            value,
            label
        })
    );

    // Lifecycle
    ngOnInit() {
        this.init();
    }

    // Carregamento
    private init() {
        const id = Number(this.route.snapshot.paramMap.get('id'));

        if (id) {
            this.isEdicao.set(true);
            this.plantaoId.set(id);
            this.buscarPlantao(id);
            return;
        }

        const escalaId = this.context.escalaId;

        if (!escalaId) {
            this.errorMessage.set('Escala inválida.');
            this.router.navigate(['/escala']);
            return;
        }

        this.escalaId.set(escalaId);
    }

    private buscarPlantao(id: number) {
        this.service.buscarPorId(id).subscribe({
            next: (p) => {
                this.plantao.set(p);

                this.form.patchValue({
                    dataInicio: new Date(p.horaEntrada),
                    dataEncerramento: new Date(p.horaSaida),
                    horaInicio: new Date(p.horaEntrada),
                    horaEncerramento: new Date(p.horaSaida),
                    comentario: p.comentario,
                });

                this.form2.patchValue({
                    quantidadeProfissionais: p.quantidadeProfissionais,
                    formaPagamento: p.formaPagamento,
                    valor: p.estimativaValor,
                    estimativaPagamento: this.toDate(p.estimativaPagamento),
                });
            },
            error: () => this.router.navigate(['/escala'])
        });
    }

    // Ações do formulário
    next() {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            return;
        }
        this.step.set(2);
    }

    back() {
        this.step.set(1);
    }

    confirmar() {
        if (this.form.invalid || this.form2.invalid) {
            this.form.markAllAsTouched();
            this.form2.markAllAsTouched();
            this.errorMessage.set('Verifique os campos.');
            return;
        }

        const payload = this.buildPayload();

        const req$ = this.isEdicao()
            ? this.service.atualizar(this.plantaoId()!, payload)
            : this.service.adicionar(payload);

        req$.subscribe({
            next: () => this.sucesso.set(true),
            error: () => this.erro.set(true)
        });
    }

    // Helpers
    private buildPayload(): PlantaoRequest {
        return {
            horaEntrada: this.buildDateTime(
                this.form.value.dataInicio!,
                this.form.value.horaInicio!
            ),
            horaSaida: this.buildDateTime(
                this.form.value.dataEncerramento!,
                this.form.value.horaEncerramento!
            ),
            estimativaValor: this.form2.value.valor!,
            formaPagamento: this.form2.value.formaPagamento!,
            estimativaPagamento: this.form2.value.estimativaPagamento
                ? this.buildDate(this.form2.value.estimativaPagamento)
                : undefined,
            comentario: this.form.value.comentario!,
            quantidadeProfissionais: this.form2.value.quantidadeProfissionais!,
            escalaId: this.isEdicao()
                ? this.plantao()!.idEscala
                : this.escalaId()!
        };
    }

    private buildDateTime(data: Date, hora: Date) {
        const d = new Date(data);
        d.setHours(hora.getHours(), hora.getMinutes(), 0, 0);
        return d.toISOString();
    }

    private buildDate(date: Date) {
        return date.toISOString().split('T')[0];
    }

    private toDate(value: string | null) {
        return value ? new Date(value) : null;
    }

    // Navegação
    onIrHome() {
        this.router.navigate(['/home']);
    }

    onIrPlantao() {
        this.router.navigate(['/plantao', this.plantaoId()]);
    }

    voltarTudo() {
        if (window.history.length > 1) {
            window.history.back();
        } else {
            this.router.navigate(['/home']);
        }
    }

    // Helper de template
    hasError(form: FormGroup, controlName: string, error: string): boolean {
        const control = form.get(controlName);
        return !!(control && control.touched && control.hasError(error));
    }

    // Modal
    fecharModal() {
        this.resetarEstado();
    }

    private resetarEstado() {
        this.step.set(1);

        this.errorMessage.set(null);

        this.form.reset();
        this.form2.reset();

        this.sucesso.set(false);
        this.erro.set(false);

        const plantaoId = Number(this.route.snapshot.paramMap.get('id'));

        if (plantaoId) {
            this.isEdicao.set(true);
            this.plantaoId.set(plantaoId);
            this.buscarPlantao(plantaoId);
            return;
        }

        if (!this.escalaId()) {
            this.errorMessage.set('Escala inválida. Acesse essa tela a partir da escala.');
            this.router.navigate(['/escala']);
            return;
        }

        this.escalaId.set(this.context.escalaId!);
        this.isEdicao.set(false);
    }

    svg = `
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevron-last-icon lucide-chevron-last"><path d="m7 18 6-6-6-6"/><path d="M17 6v12"/></svg>
    `

    svg4 = `
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
        <path d="M14.4888 13.7466L9.48875 18.7466C9.3489 18.8866 9.17064 18.9819 8.97656 19.0206C8.78247 19.0592 8.58128 19.0394 8.39846 18.9637C8.21564 18.8879 8.0594 18.7596 7.94953 18.595C7.83966 18.4305 7.78109 18.237 7.78125 18.0391V14.0391H-0.21875C-0.483966 14.0391 -0.73832 13.9337 -0.925857 13.7462C-1.11339 13.5586 -1.21875 13.3043 -1.21875 13.0391C-1.21875 12.7738 -1.11339 12.5195 -0.925857 12.332C-0.73832 12.1444 -0.483966 12.0391 -0.21875 12.0391H7.78125V8.03906C7.78109 7.84117 7.83966 7.64767 7.94953 7.48308C8.0594 7.31849 8.21564 7.19019 8.39846 7.11444C8.58128 7.03869 8.78247 7.01888 8.97656 7.05753C9.17064 7.09618 9.3489 7.19155 9.48875 7.33156L14.4888 12.3316C14.5817 12.4244 14.6555 12.5347 14.7058 12.6561C14.7561 12.7775 14.782 12.9076 14.782 13.0391C14.782 13.1705 14.7561 13.3006 14.7058 13.422C14.6555 13.5434 14.5817 13.6537 14.4888 13.7466ZM21.7812 1.03906H13.7812C13.516 1.03906 13.2617 1.14442 13.0741 1.33196C12.8866 1.51949 12.7812 1.77385 12.7812 2.03906C12.7812 2.30428 12.8866 2.55863 13.0741 2.74617C13.2617 2.93371 13.516 3.03906 13.7812 3.03906H20.7812V23.0391H13.7812C13.516 23.0391 13.2617 23.1444 13.0741 23.332C12.8866 23.5195 12.7812 23.7738 12.7812 24.0391C12.7812 24.3043 12.8866 24.5586 13.0741 24.7462C13.2617 24.9337 13.516 25.0391 13.7812 25.0391H21.7812C22.0465 25.0391 22.3008 24.9337 22.4884 24.7462C22.6759 24.5586 22.7812 24.3043 22.7812 24.0391V2.03906C22.7812 1.77385 22.6759 1.51949 22.4884 1.33196C22.3008 1.14442 22.0465 1.03906 21.7812 1.03906Z" fill="white"/>
        </svg>
    `

    svg3 = `
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#020035" viewBox="0 0 256 256"><path fill="currentColor" d="M240,208H224V136l2.34,2.34A8,8,0,0,0,237.66,127L139.31,28.68a16,16,0,0,0-22.62,0L18.34,127a8,8,0,0,0,11.32,11.31L32,136v72H16a8,8,0,0,0,0,16H240a8,8,0,0,0,0-16Zm-88,0H104V160a4,4,0,0,1,4-4h40a4,4,0,0,1,4,4Z"></path></svg>
    `
}