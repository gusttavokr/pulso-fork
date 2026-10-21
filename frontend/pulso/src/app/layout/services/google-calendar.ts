import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';
import { GoogleCalendarAuthUrl } from '../components/sidebars/sidebar-calendario/models/google-calendar-auth-url';
import { GoogleCalendarEvent } from '../components/sidebars/sidebar-calendario/models/google-calendar-event';
import { GoogleCalendarStatus } from '../components/sidebars/sidebar-calendario/models/google-calendar-status';

@Injectable({
    providedIn: 'root'
})
export class GoogleCalendarService {

    private readonly http = inject(HttpClient);

    private readonly apiUrl = 'http://localhost:8080/api/google-calendar';

    // Connection state
    private connectionChangedSubject = new Subject<boolean>();

    connectionChanged$ = this.connectionChangedSubject.asObservable();

    // Auth
    getAuthUrl(): Observable<GoogleCalendarAuthUrl> {

        return this.http.get<GoogleCalendarAuthUrl>(
            `${this.apiUrl}/auth-url`
        );

    }

    handleCallback(code: string): Observable<{ message: string }> {

        return this.http.post<{ message: string }>(
            `${this.apiUrl}/callback`,
            null,
            { params: { code } }
        ).pipe(
            tap(() => this.connectionChangedSubject.next(true))
        );

    }

    // Events
    getUpcomingEvents(maxResults: number = 10): Observable<GoogleCalendarEvent[]> {

        return this.http.get<GoogleCalendarEvent[]>(
            `${this.apiUrl}/events`,
            {
                params: { maxResults: maxResults.toString() }
            }
        );

    }

    // Connection
    getConnectionStatus(): Observable<GoogleCalendarStatus> {

        return this.http.get<GoogleCalendarStatus>(
            `${this.apiUrl}/status`
        );

    }

    disconnect(): Observable<{ message: string }> {

        return this.http.delete<{ message: string }>(
            `${this.apiUrl}/disconnect`
        ).pipe(
            tap(() => this.connectionChangedSubject.next(false))
        );

    }

}