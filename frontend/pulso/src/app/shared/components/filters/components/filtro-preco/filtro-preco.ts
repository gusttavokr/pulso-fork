import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';

@Component({
    selector: 'app-filtro-preco',
    imports: [CommonModule, FormsModule, InputTextModule, FloatLabel],
    templateUrl: 'filtro-preco.html',
})
export class FiltroPrecoComponent implements OnChanges {

    @Input() resetTrigger!: number;

    @Output() filtroChange = new EventEmitter<{
        preco?: { min?: number; max?: number };
    }>();

    valorMin: number | null = null;
    valorMax: number | null = null;

    ngOnChanges(changes: SimpleChanges) {
        if (changes['resetTrigger']) {
            this.resetar();
        }
    }

    private resetar() {
        this.valorMin = null;
        this.valorMax = null;
        this.filtroChange.emit({ preco: undefined });
    }

    onChange() {

        let min = this.valorMin ?? undefined;
        let max = this.valorMax ?? undefined;

        if (min !== undefined && max !== undefined && min > max) {
            [min, max] = [max, min];
        }

        this.filtroChange.emit({
            preco: (min !== undefined || max !== undefined)
                ? { min, max }
                : undefined
        });

    }

}