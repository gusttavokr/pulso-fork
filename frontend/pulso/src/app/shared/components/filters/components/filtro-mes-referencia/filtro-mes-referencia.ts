import { CommonModule } from "@angular/common";
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { SelectModule } from "primeng/select";
import { Mes, MES_LABEL } from "../../../../enums/mes";

@Component({
    selector: 'app-filtro-mes-referencia',
    imports: [CommonModule, FormsModule, SelectModule],
    templateUrl: 'filtro-mes-referencia.html'
})
export class FiltroMesReferenciaComponent implements OnChanges {

    @Input() resetTrigger!: number;

    @Output() filtroChange = new EventEmitter<{
        mes?: Mes;
    }>();

    selected: Mes | null = null;

    meses = Object.values(Mes).filter(v => typeof v === 'number') as Mes[];

    options = this.meses.map(m => ({
        label: MES_LABEL[m],
        value: m
    }));

    ngOnChanges(changes: SimpleChanges) {
        if (changes['resetTrigger']) {
            this.resetar();
        }
    }

    private resetar() {
        this.selected = null;
        this.filtroChange.emit({ mes: undefined });
    }

    onChange(value: Mes | null) {
        this.filtroChange.emit({
            mes: value ?? undefined
        });
    }
}