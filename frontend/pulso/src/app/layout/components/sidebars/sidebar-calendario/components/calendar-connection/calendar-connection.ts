import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-calendar-connection',
    imports: [CommonModule],
    templateUrl: './calendar-connection.html'
})
export class CalendarConnectionComponent {

    @Input() isConnected = false;

    @Output() connect = new EventEmitter<void>();
    @Output() disconnect = new EventEmitter<void>();

}