import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, EventEmitter, inject, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CheckboxModule } from 'primeng/checkbox';
import { CATEGORIA_LABEL, CategoriaFiltro } from '../../../../enums/categoria-filtro';

@Component({
    selector: 'app-filtro-categoria',
    imports: [CommonModule, FormsModule, CheckboxModule],
    templateUrl: 'filtro-categoria.html'
})
export class FiltroCategoriaComponent implements OnChanges {

    @Input() resetTrigger!: number;

    @Output() filtroChange = new EventEmitter<{
        categorias?: CategoriaFiltro[];
    }>();

    categories = Object.values(CategoriaFiltro);

    selected: CategoriaFiltro[] = [];

    readonly labels = CATEGORIA_LABEL;

    ngOnChanges(changes: SimpleChanges) {
        if (changes['resetTrigger']) {
            this.resetar();
        }
    }

    private resetar() {
        this.selected = [];
        this.filtroChange.emit({ categorias: undefined });
    }

    emitChange() {
        this.filtroChange.emit({
            categorias: this.selected.length ? this.selected : undefined
        });
    }
}