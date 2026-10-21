import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { PieChartComponent } from './components/pie-chart/pie-chart';
import { PieLegendComponent } from './components/pie-legend/pie-legend';
import { InstituicaoService } from '../../../instituicoes/services/instituicao';

export enum ICON {
    SINO = 'SINO'
}

export const ICON_SVGS: Record<ICON, string> = {
    [ICON.SINO]: `
<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#2d69ff" viewBox="0 0 256 256">
<path d="M253.66,133.66l-32,32a8,8,0,0,1-11.32,0l-16-16a8,8,0,0,1,11.32-11.32L216,148.69l26.34-26.35a8,8,0,0,1,11.32,11.32ZM144,157.68a68,68,0,1,0-71.9,0c-20.65,6.76-39.23,19.39-54.17,37.17A8,8,0,0,0,24,208H192a8,8,0,0,0,6.13-13.15C183.18,177.07,164.6,164.44,144,157.68Z"></path>
</svg>`
};

@Component({
    selector: 'app-card-profissionais-alocados',
    imports: [
        CommonModule,
        CardModule,
        PieChartComponent,
        PieLegendComponent
    ],
    templateUrl: 'card-profissionais-alocados.html'
})
export class CardProfissionaisAlocadosComponent implements OnInit {

    private instituicaoService = inject(InstituicaoService);
    private sanitizer = inject(DomSanitizer);

    iconHtml: SafeHtml = this.sanitizer.bypassSecurityTrustHtml(ICON_SVGS[ICON.SINO]);

    total = 0;
    alocados = 0;
    naoAlocados = 0;

    percentAlocados = 0;
    percentNaoAlocados = 0;

    alocadosPath = '';
    naoAlocadosPath = '';

    dividerAngleRad: number | null = null;

    private animationDuration = 800;

    ngOnInit(): void {

        this.instituicaoService.obterStatus()
            .pipe(
                catchError(() =>
                    of({ profissionais: { total: 0, alocados: 0, naoAlocados: 0 } })
                )
            )
            .subscribe((res: any) => {

                const p = res?.profissionais ?? { total: 0, alocados: 0, naoAlocados: 0 };

                this.total = Number(p.total ?? 0);
                this.alocados = Number(p.alocados ?? 0);
                this.naoAlocados = Number(p.naoAlocados ?? 0);

                if (this.total <= 0) {
                    this.percentAlocados = 0;
                    this.percentNaoAlocados = 0;
                    return;
                }

                this.percentAlocados = (this.alocados / this.total) * 100;
                this.percentNaoAlocados = 100 - this.percentAlocados;

                const targetFrac = this.percentAlocados / 100;

                if (targetFrac === 0 || targetFrac === 1) {
                    this.dividerAngleRad = this.fractionToRadians(0);
                } else {
                    this.dividerAngleRad = null;
                }

                this.animatePie(targetFrac);
            });
    }

    private animatePie(targetFrac: number) {

        const start = performance.now();
        const duration = this.animationDuration;
        const radius = 40;

        const step = (ts: number) => {

            const elapsed = ts - start;
            const t = Math.min(1, elapsed / duration);

            const ease = 1 - Math.pow(1 - t, 3);

            const currentFrac = ease * targetFrac;

            this.alocadosPath = this.describeSector(0, currentFrac, radius);
            this.naoAlocadosPath = this.describeSector(currentFrac, 1, radius);

            if (t < 1) {
                requestAnimationFrame(step);
            } else {
                this.alocadosPath = this.describeSector(0, targetFrac, radius);
                this.naoAlocadosPath = this.describeSector(targetFrac, 1, radius);
            }
        };

        if (targetFrac === 0) {
            this.alocadosPath = '';
            this.naoAlocadosPath = this.fullCirclePath(radius);
            return;
        }

        if (targetFrac === 1) {
            this.alocadosPath = this.fullCirclePath(radius);
            this.naoAlocadosPath = '';
            return;
        }

        requestAnimationFrame(step);
    }

    private describeSector(startFraction: number, endFraction: number, r: number): string {

        if (endFraction <= startFraction) return '';

        const startAngle = this.fractionToRadians(startFraction);
        const endAngle = this.fractionToRadians(endFraction);

        const start = this.polarToCartesian(0, 0, r, endAngle);
        const end = this.polarToCartesian(0, 0, r, startAngle);

        const largeArcFlag = (endFraction - startFraction) > 0.5 ? 1 : 0;

        return `M 0 0 L ${start.x} ${start.y} A ${r} ${r} 0 ${largeArcFlag} 0 ${end.x} ${end.y} Z`;
    }

    private fractionToRadians(f: number): number {
        return (f * 360 - 90) * (Math.PI / 180);
    }

    private polarToCartesian(cx: number, cy: number, r: number, angleRad: number) {

        return {
            x: +(cx + r * Math.cos(angleRad)).toFixed(4),
            y: +(cy + r * Math.sin(angleRad)).toFixed(4)
        };
    }

    private fullCirclePath(r: number): string {

        return `
        M 0 ${-r}
        A ${r} ${r} 0 1 1 0 ${r}
        A ${r} ${r} 0 1 1 0 ${-r}
        Z`;
    }

    get dividerX() {
        if (this.dividerAngleRad === null) return 0;
        return 40 * Math.cos(this.dividerAngleRad);
    }

    get dividerY() {
        if (this.dividerAngleRad === null) return 0;
        return 40 * Math.sin(this.dividerAngleRad);
    }

    get dividerColor(): string {

        if (this.percentAlocados === 100) return '#fff';

        if (this.percentNaoAlocados === 100) return '#2d69ff';

        return 'transparent';
    }
}