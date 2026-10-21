import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DatePickerModule } from 'primeng/datepicker';

@Component({
    selector: 'app-filtro-data',
    imports: [CommonModule, FormsModule, DatePickerModule],
    templateUrl: 'filtro-data.html',
})
export class FiltroDataComponent implements OnChanges {

    @Input() resetTrigger!: number;

    @Output() filtroChange = new EventEmitter<{
        data?: string
    }>();

    date: Date | null = new Date();

    ngOnChanges(changes: SimpleChanges) {
        if (changes['resetTrigger']) {
            this.resetar();
        }
    }

    private resetar() {
        this.date = new Date();
        this.filtroChange.emit({ data: undefined });
    }

    onDateChange() {

        if (!this.date) {
            this.filtroChange.emit({ data: undefined });
            return;
        }

        const dataFormatada = this.formatarData(this.date);

        this.filtroChange.emit({
            data: dataFormatada
        });
    }

    private formatarData(date: Date): string {
        return date.toISOString().split('T')[0];
    }
    
}