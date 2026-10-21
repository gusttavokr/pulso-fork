import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SelectModule } from 'primeng/select';
import { Especialidade, ESPECIALIDADE_LABEL } from '../../../../enums/especialidade';

@Component({
    selector: 'app-filtro-especialidade',
    imports: [CommonModule, FormsModule, SelectModule],
    templateUrl: 'filtro-especialidade.html',
})
export class FiltroEspecialidadeComponent implements OnChanges {

    @Input() resetTrigger!: number;

    @Output() filtroChange = new EventEmitter<{
        especialidade?: Especialidade;
    }>();

    selected: Especialidade | null = null;

    readonly labels = ESPECIALIDADE_LABEL;

    especialidades = Object.values(Especialidade).map(e => ({
        name: this.labels[e],
        value: e
    }));

    ngOnChanges(changes: SimpleChanges) {
        if (changes['resetTrigger']) {
            this.resetar();
        }
    }

    private resetar() {
        this.selected = null;
        this.filtroChange.emit({ especialidade: undefined });
    }

    onChange(value: Especialidade | null) {

        this.filtroChange.emit({
            especialidade: value ?? undefined
        });

    }
}