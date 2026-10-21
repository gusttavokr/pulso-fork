import { Component, computed, EventEmitter, inject, Input, Output } from "@angular/core";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { PlantaoEscalaSimple } from "../../../plantoes/models/plantao-escala-simple";
import { getEspecialidadeIcon } from "../../../../shared/enums/especialidade-icon";
import { ESPECIALIDADE_LABEL } from "../../../../shared/enums/especialidade";
import { ButtonComponent } from "../../../../shared/components/button/button";
import { CommonModule } from "@angular/common";

@Component({
    selector: 'app-detalhe-plantao-popover',
    imports: [
        CommonModule,
        ButtonComponent
    ],
    templateUrl: 'detalhe-plantao-popover.html'
})
export class DetalhePlantaoPopoverComponent {

    private readonly sanitizer = inject(DomSanitizer);

    @Input({ required: true })
    shift!: PlantaoEscalaSimple;

    @Input({ required: true })
    position!: { x: number; y: number };

    @Output()
    closed = new EventEmitter<void>();

    @Output()
    viewDetails = new EventEmitter<number>();

    // Derivados
    readonly labelEspecialidade = computed(() =>
        ESPECIALIDADE_LABEL[this.shift.especialidade]
    );

    readonly iconeEspecialidade = computed<SafeHtml>(() =>
        this.sanitizer.bypassSecurityTrustHtml(
            getEspecialidadeIcon(this.shift.especialidade)
        )
    );

    readonly duracaoHoras = computed(() => {
        const inicio = new Date(this.shift.horaEntrada);
        const fim = new Date(this.shift.horaSaida);

        const diffMs = fim.getTime() - inicio.getTime();
        return Math.round(diffMs / (1000 * 60 * 60));
    });

    readonly status = computed(() => {
        const agora = new Date();
        const inicio = new Date(this.shift.horaEntrada);

        const diffHoras =
            (inicio.getTime() - agora.getTime()) / (1000 * 60 * 60);

        const alocados = this.shift.profissionaisAlocados.length;
        const necessario = this.shift.qtdProfissionais;

        if (alocados === 0 && diffHoras > 0 && diffHoras <= 24) {
            return { label: 'Urgente', color: '#FF4141' };
        }

        if (diffHoras > 0 && diffHoras <= 24) {
            return { label: 'Próximo', color: '#FFB200' };
        }

        if (alocados === 0) {
            return { label: 'Aguardando', color: '#FFB200' };
        }

        if (alocados < necessario) {
            return { label: 'Em aberto', color: '#FFB200' };
        }

        if (alocados === necessario) {
            return { label: 'Completo', color: '#21C694' };
        }

        return { label: 'Excedente', color: '#FF4141' };
    });

    // Ações
    handleClose(): void {
        this.closed.emit();
    }

    handleViewDetails(): void {
        this.viewDetails.emit(this.shift.id);
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
}