import { CommonModule } from "@angular/common";
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { SelectModule } from "primeng/select";
import { STATUS_ESCALA_CONFIG, StatusEscala } from "../../../../enums/status-escala";

@Component({
    selector: 'app-filtro-status-escala',
    imports: [CommonModule, FormsModule, SelectModule],
    templateUrl: 'filtro-status-escala.html'
})
export class FiltroStatusEscalaComponent implements OnChanges {

    @Input() resetTrigger!: number;

    @Output() filtroChange = new EventEmitter<{
        status?: StatusEscala;
    }>();

    selected: StatusEscala | null = null;

    options = Object.values(StatusEscala).map(status => ({
        label: STATUS_ESCALA_CONFIG[status],
        value: status
    }));

    ngOnChanges(changes: SimpleChanges) {
        if (changes['resetTrigger']) {
            this.resetar();
        }
    }

    private resetar() {
        this.selected = null;
        this.filtroChange.emit({ status: undefined });
    }

    onChange(value: StatusEscala | null) {
        this.filtroChange.emit({
            status: value ?? undefined
        });
    }

}