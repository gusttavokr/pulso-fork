import { Component, inject, OnInit, OnDestroy, Output, EventEmitter, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

import { CalendarConnectionComponent } from './components/calendar-connection/calendar-connection';
import { CalendarEventGroupComponent } from './components/calendar-event-group/calendar-event-group';

import { SidebarCalendarioModel } from './models/sidebar-calendar';
import { GoogleCalendarEvent } from './models/google-calendar-event';
import { CalendarGroup } from './models/calendar-group';

import {
    agruparEventosPorData
} from './utils/calendar-utils';
import { Especialidade } from '../../../../shared/enums/especialidade';
import { GoogleCalendarService } from '../../../services/google-calendar';
import { SidebarService } from '../../../services/sidebar';
import { ButtonComponent } from '../../../../shared/components/button/button';

@Component({
    selector: 'app-sidebar-calendario',
    imports: [
        CommonModule,
        CalendarConnectionComponent,
        CalendarEventGroupComponent,
        ButtonComponent
    ],
    templateUrl: './sidebar-calendar.html'
})
export class SidebarCalendarComponent implements OnInit, OnDestroy {

    private readonly googleCalendarService = inject(GoogleCalendarService);
    readonly sidebarService = inject(SidebarService);

    private connectionSub?: Subscription;
    private refreshInterval?: any;

    isConnected = signal(false);
    isLoading = signal(true);

    svg = `
        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
        </svg>
    `

    lista: SidebarCalendarioModel[] = [];
    grupos: CalendarGroup[] = [];

    ngOnInit(): void {

        this.checkConnectionAndLoadEvents();

        this.connectionSub =
            this.googleCalendarService.connectionChanged$
                .subscribe({
                    next: (connected) => {
                        this.isConnected.set(connected);

                        if (connected) {
                            this.loadEvents();
                            this.startAutoRefresh();
                        } else {
                            this.lista = [];
                            this.grupos = [];
                            // this.loadMockData();
                            this.stopAutoRefresh();
                        }
                    }
                });
    }

    ngOnDestroy(): void {

        this.connectionSub?.unsubscribe();
        this.stopAutoRefresh();

    }

    startAutoRefresh(): void {

        this.stopAutoRefresh();

        this.refreshInterval = setInterval(() => {

            if (this.isConnected()) {
                this.loadEvents();
            }

        }, 30000);

    }

    stopAutoRefresh(): void {

        if (this.refreshInterval) {
            clearInterval(this.refreshInterval);
            this.refreshInterval = undefined;
        }

    }

    checkConnectionAndLoadEvents(): void {

        this.isLoading.set(true);

        this.googleCalendarService
            .getConnectionStatus()
            .subscribe({
                next: (status) => {

                    this.isConnected.set(status.connected);

                    if (this.isConnected()) {

                        this.loadEvents();
                        this.startAutoRefresh();

                    } else {

                        this.lista = [];
                        this.grupos = [];
                        // this.loadMockData();
                        this.isLoading.set(false);
                        // this.stopAutoRefresh();

                    }
                }
            });
    }

    loadEvents(): void {

        this.googleCalendarService
            .getUpcomingEvents(20)
            .subscribe(events => {

                this.lista = this.convertGoogleEvents(events);
                this.grupos = agruparEventosPorData(this.lista);

                this.isLoading.set(false);

            });

    }

    convertGoogleEvents(events: GoogleCalendarEvent[]): SidebarCalendarioModel[] {

        return events
            .map((event, index) => {

                let startDate: Date;
                let hours = 0;
                let minutes = 0;

                if (event.start?.dateTime) {

                    startDate = new Date(event.start.dateTime);
                    hours = startDate.getHours();
                    minutes = startDate.getMinutes();

                } else if (event.start?.date) {

                    startDate = new Date(event.start.date);
                    hours = 0;
                    minutes = 0;

                } else {

                    startDate = new Date();
                    hours = startDate.getHours();
                    minutes = startDate.getMinutes();

                }

                if (isNaN(startDate.getTime())) {

                    startDate = new Date();
                    hours = startDate.getHours();
                    minutes = startDate.getMinutes();

                }

                return {
                    id: index + 1,
                    dia: startDate,
                    horario: { hours, minutes },
                    especialidade: this.detectEspecialidade(event.summary || ''),
                    titulo: event.summary || 'Sem título'
                };

            })
            .sort((a, b) => {

                const diff = a.dia.getTime() - b.dia.getTime();
                if (diff !== 0) return diff;

                if (a.horario.hours !== b.horario.hours) {
                    return a.horario.hours - b.horario.hours;
                }

                return a.horario.minutes - b.horario.minutes;

            });

    }

    private readonly especialidadeMap = [
        { keywords: ['plantão'], tipo: Especialidade.CLINICA_GERAL },
        { keywords: ['cardio'], tipo: Especialidade.CARDIOLOGIA },
        { keywords: ['neuro'], tipo: Especialidade.NEUROLOGIA },
        { keywords: ['odonto'], tipo: Especialidade.ODONTOLOGIA },
        { keywords: ['reunião', 'reuniao'], tipo: Especialidade.REUNIAO },
        { keywords: ['urgência', 'urgencia'], tipo: Especialidade.URGENCIA },
        { keywords: ['pedi'], tipo: Especialidade.PEDIATRIA },
        { keywords: ['orto'], tipo: Especialidade.ORTOPEDIA },
        { keywords: ['dermato'], tipo: Especialidade.DERMATOLOGIA },
        { keywords: ['oftalmo'], tipo: Especialidade.OFTALMOLOGIA },
        { keywords: ['psico'], tipo: Especialidade.PSICOLOGIA }
    ];

    detectEspecialidade(summary: string): Especialidade {

        const titulo = summary.toLowerCase();

        const match = this.especialidadeMap.find(item =>
            item.keywords.some(keyword => titulo.includes(keyword))
        );

        return match?.tipo ?? Especialidade.CLINICA_GERAL;

    }

    connectGoogleCalendar() {

        this.googleCalendarService.getAuthUrl()
            .subscribe(res => {

                sessionStorage.setItem(
                    'googleCalendarRedirectUrl',
                    window.location.pathname
                );

                window.location.href = res.authUrl;

            });

    }

    disconnectGoogleCalendar() {

        if (!confirm('Deseja desconectar o Google Calendar?')) return;

        this.googleCalendarService.disconnect()
            .subscribe();

    }

    inverterSidebar(): void {

        const aberta = !this.sidebarService.sidebarAberta();

        this.sidebarService.setSidebarAberta(aberta);

    }

    mostrarSidebar(): void {

        this.sidebarService.setSidebarAberta(true);

    }

    ocultarSidebar(): void {

        this.sidebarService.setSidebarAberta(false);

    }

}