import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CheckboxModule } from 'primeng/checkbox';
import { CategoriaHorarioEnum } from '../../../../enums/categoria-horario';

type CategoriaHorario = {
    key: CategoriaHorarioEnum;
    label: string;
    inicio: string;
    fim: string;
};

@Component({
    selector: 'app-filtro-horario',
    imports: [CommonModule, FormsModule, CheckboxModule],
    templateUrl: 'filtro-horario.html',
})
export class FiltroHorarioComponent implements OnChanges {

    @Input() resetTrigger!: number;

    @Output() filtroChange = new EventEmitter<{
        horarios?: { inicio: string; fim: string }[];
    }>();

    categorias: CategoriaHorario[] = [
        {
            key: CategoriaHorarioEnum.MANHA,
            label: 'Manhã (07:00 - 13:00)',
            inicio: '07:00',
            fim: '13:00'
        },
        {
            key: CategoriaHorarioEnum.TARDE,
            label: 'Tarde (13:00 - 19:00)',
            inicio: '13:00',
            fim: '19:00'
        },
        {
            key: CategoriaHorarioEnum.NOITE,
            label: 'Noite (19:00 - 07:00)',
            inicio: '19:00',
            fim: '07:00'
        }
    ];

    selected: CategoriaHorario[] = [];

    ngOnChanges(changes: SimpleChanges) {
        if (changes['resetTrigger']) {
            this.resetar();
        }
    }

    private resetar() {
        this.selected = [];
        this.filtroChange.emit({ horarios: undefined });
    }

    onChange() {
        const horarios = this.selected.map(cat => ({
            inicio: cat.inicio,
            fim: cat.fim
        }));

        this.filtroChange.emit({
            horarios: horarios.length ? horarios : undefined
        });
    }

}