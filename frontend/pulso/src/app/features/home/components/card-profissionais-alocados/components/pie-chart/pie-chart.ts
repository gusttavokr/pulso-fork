import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-pie-chart',
    imports: [CommonModule],
    templateUrl: 'pie-chart.html'
})
export class PieChartComponent {

    @Input() alocadosPath!: string;
    @Input() naoAlocadosPath!: string;

    @Input() dividerAngleRad!: number | null;
    @Input() dividerX!: number;
    @Input() dividerY!: number;
    @Input() dividerColor!: string;
}