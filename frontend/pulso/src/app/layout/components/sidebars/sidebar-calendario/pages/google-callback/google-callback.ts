import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

import { GoogleCalendarService } from '../../../../../services/google-calendar';
import { FeedbackModalComponent } from '../../../../../../shared/components/modals/feedback-modal/feedback-modal';

@Component({
    selector: 'app-google-callback',
    imports: [
        CommonModule, 
        FeedbackModalComponent
    ],
    templateUrl: './google-callback.html'
})
export class GoogleCallbackComponent implements OnInit {

    private readonly route = inject(ActivatedRoute);
    private readonly router = inject(Router);
    private readonly googleCalendarService = inject(GoogleCalendarService);

    isProcessing = signal(true);
    error = signal<string | null>(null);
    success = signal(false);

    ngOnInit(): void {

        this.route.queryParams.subscribe(params => {

            const code = params['code'];
            const error = params['error'];

            if (error) {
                this.handleAuthError('Autorização negada pelo usuário.');
                return;
            }

            if (code) {
                this.handleCallback(code);
            } else {
                this.handleAuthError('Código de autorização não encontrado.');
            }

        });

    }

    private handleCallback(code: string): void {

        this.googleCalendarService.handleCallback(code).subscribe({

            next: () => {

                this.success.set(true);
                this.isProcessing.set(false);

                const redirectUrl =
                    sessionStorage.getItem('googleCalendarRedirectUrl') || '/home';

                sessionStorage.removeItem('googleCalendarRedirectUrl');

                setTimeout(() => {
                    this.router.navigate([redirectUrl]);
                }, 800);

            },

            error: (err) => {

                this.isProcessing.set(false);
                this.error = err.error?.error || 'Erro ao processar autorização.';

            }

        });

    }

    private handleAuthError(message: string): void {

        this.isProcessing.set(false);
        this.error.set(message);

    }

    goHome(): void {
        this.router.navigate(['/home']);
    }

}