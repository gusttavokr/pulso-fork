import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-pie-legend',
    imports: [CommonModule],
    templateUrl: 'pie-legend.html'
})
export class PieLegendComponent {

    @Input() percentAlocados!: number;
    @Input() percentNaoAlocados!: number;
}