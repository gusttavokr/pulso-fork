import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarCalendarioModel } from '../../models/sidebar-calendar';
import { formatarHorario } from '../../utils/calendar-utils';

@Component({
    selector: 'app-calendar-event-card',
    imports: [CommonModule],
    templateUrl: './calendar-event-card.html'
})
export class CalendarEventCardComponent {

    @Input() item!: SidebarCalendarioModel;
    @Input() index!: number;

    formatarHorario(): string {

        return formatarHorario(
            this.item.horario.hours,
            this.item.horario.minutes
        );

    }

}