import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarEventCardComponent } from '../calendar-event-card/calendar-event-card';
import { SidebarCalendarioModel } from '../../models/sidebar-calendar';
import { cabecalhoData } from '../../utils/calendar-utils';

@Component({
    selector: 'app-calendar-event-group',
    imports: [
        CommonModule,
        CalendarEventCardComponent
    ],
    templateUrl: './calendar-event-group.html'
})
export class CalendarEventGroupComponent {

    @Input() data!: Date;
    @Input() itens: SidebarCalendarioModel[] = [];

    cabecalho(): string {
        return cabecalhoData(this.data);
    }

}