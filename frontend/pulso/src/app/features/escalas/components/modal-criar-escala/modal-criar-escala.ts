import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from "@angular/core";
import { EscalaAPIService } from "../../services/escala";
import { InstituicaoService } from "../../../instituicoes/services/instituicao";
import { Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { InstituicaoResponse } from "../../../instituicoes/models/instituicao-response";
import { SetorSimple } from "../../../setor/models/setor-simple";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { EscalaRequest } from "../../models/escala-request";
import { ModalComponent } from "../../../../shared/components/modal/modal";
import { CommonModule } from "@angular/common";
import { ButtonComponent } from "../../../../shared/components/button/button";
import { DatePickerModule } from "primeng/datepicker";
import { SelectModule } from "primeng/select";

@Component({
    selector: 'app-criar-escala-modal',
    imports: [
        CommonModule,
        ReactiveFormsModule,
        DatePickerModule,
        SelectModule,
        ModalComponent,
        ButtonComponent,
    ],
    templateUrl: 'modal-criar-escala.html'
})
export class CriarEscalaModalComponent implements OnInit {

    private readonly escalaService = inject(EscalaAPIService);
    private readonly instituicaoService = inject(InstituicaoService);
    private readonly router = inject(Router);
    private readonly sanitizer = inject(DomSanitizer);

    @Input() visible = false;

    @Output() closed = new EventEmitter<void>();
    @Output() created = new EventEmitter<number>();
    @Output() error = new EventEmitter<void>();

    // State
    step = signal<1 | 2>(1);
    loading = signal(false);
    errorMessage = signal<string | null>(null);

    instituicoes: InstituicaoResponse[] = [];
    private instituicoesOriginais: InstituicaoResponse[] = [];

    setoresDisponiveis: SetorSimple[] = [];
    liderEspecialidades: string[] = [];

    // Forms
    form = new FormGroup({
        nome: new FormControl('', Validators.required),
        instituicaoId: new FormControl<number | null>(null, Validators.required),
        setorId: new FormControl<number | null>(null, Validators.required),
    });

    form2 = new FormGroup({
        mes: new FormControl<Date | null>(null, Validators.required),
        copia: new FormControl<boolean>(false),
    });

    // Getters
    get f() {
        return this.form.controls;
    }

    get f2() {
        return this.form2.controls;
    }

    // Init
    ngOnInit(): void {
        this.initData();
    }

    private initData(): void {
        this.instituicaoService.listar({ doLider: true }).subscribe({
            next: res => {
                this.instituicoesOriginais = res;
                this.instituicoes = res.map(i => ({ ...i }));

                if (res.length) {
                    // this.f.instituicaoId.setValue(res[0].id);
                    this.updateSetores();
                }
            },
            error: () => this.handleError('Erro ao carregar instituições.')
        });
    }

    // From logic
    onInstChange(): void {
        this.updateSetores();
    }

    private updateSetores(): void {
        const idInst = this.f.instituicaoId.value;
        const inst = this.instituicoesOriginais.find(i => i.id === idInst);

        if (!inst) {
            this.setoresDisponiveis = [];
            this.f.setorId.reset();
            return;
        }

        this.setoresDisponiveis = inst.setores.filter(setor =>
            !this.liderEspecialidades.length ||
            this.liderEspecialidades.includes(setor.especialidade)
        );

        // this.f.setorId.setValue(
        //     this.setoresDisponiveis[0]?.id ?? null
        // );
    }

    // Steps
    next(): void {
        if (!this.validateForms(this.form)) return;
        this.step.set(2);
    }

    back(): void {
        this.errorMessage.set(null);
        this.step.set(1);
    }

    // Create
    create(): void {

        if (!this.validateForms(this.form, this.form2)) return;
        if (this.loading()) return;

        const payload = this.buildPayload();

        this.loading.set(true);

        this.escalaService.adicionar(payload).subscribe({
            next: escala => {
                this.loading.set(false);
                this.resetForms();
                this.created.emit(escala.id);
                this.close();
            },
            error: () => {
                this.loading.set(false);
                this.resetForms();
                this.error.emit();
                this.close();
            }
        });
    }

    private buildPayload(): EscalaRequest {
        const mes = this.f2.mes.value!;

        const mesReferencia =
            `${mes.getFullYear()}-${String(mes.getMonth() + 1).padStart(2, '0')}-01`;

        return {
            nome: this.f.nome.value!,
            setorId: this.f.setorId.value!,
            mes: mesReferencia,
            copia: this.f2.copia.value!,
        };
    }

    private resetForms(): void {
        this.form.reset();
        this.form2.reset();

        this.form2.patchValue({
            copia: false
        });
    }

    // Utils
    private validateForms(...forms: FormGroup[]): boolean {
        this.errorMessage.set(null);

        const invalid = forms.some(f => f.invalid);

        if (invalid) {
            forms.forEach(f => f.markAllAsTouched());
            this.errorMessage.set('Preencha todos os campos obrigatórios.');
            return false;
        }

        return true;
    }

    private handleError(message: string): void {
        this.errorMessage.set(message);
    }

    // Close
    close(): void {
        this.closed.emit();
    }

    svg1 = `
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevron-last-icon lucide-chevron-last"><path d="m7 18 6-6-6-6"/><path d="M17 6v12"/></svg>
    `

    svg3 = `
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
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-chevron-left-icon lucide-chevron-left"><path d="m15 18-6-6 6-6"/></svg>
    `
}