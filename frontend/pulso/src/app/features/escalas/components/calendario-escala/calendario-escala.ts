import { Component, computed, inject, Input, signal } from "@angular/core";
import { PlantaoEscalaSimple } from "../../../plantoes/models/plantao-escala-simple";
import { CardPlantaoEscalaComponent } from "../card-plantao-escala/card-plantao-escala";
import { DetalhePlantaoPopoverComponent } from "../detalhe-plantao-popover/detalhe-plantao-popover";
import { Router } from "@angular/router";

@Component({
    selector: 'app-calendario-escala',
    imports: [
        CardPlantaoEscalaComponent,
        DetalhePlantaoPopoverComponent
    ],
    templateUrl: 'calendario-escala.html'
})
export class CalendarioEscalaComponent {

    private readonly router = inject(Router);

    @Input({ required: true })
    mesReferencia!: Date;

    @Input({ required: true })
    plantoes!: PlantaoEscalaSimple[];

    // UI State
    readonly selectedShift = signal<PlantaoEscalaSimple | null>(null);
    readonly isPopoverVisible = signal(false);
    readonly popoverPosition = signal({ x: 0, y: 0 });

    // Calendário
    readonly diasSemana = ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'];

    readonly currentWeekIndex = signal(0);

    private getDateKey(date: Date): string {
        return `${date.getUTCFullYear()}-${date.getUTCMonth()}-${date.getUTCDate()}`;
    }

    readonly weeks = computed(() => {
        const semanas: Date[][] = [];

        const ano = this.mesReferencia.getFullYear();
        const mes = this.mesReferencia.getMonth();

        const primeiroDiaMes = new Date(ano, mes, 1);
        const ultimoDiaMes = new Date(ano, mes + 1, 0);

        const inicio = new Date(primeiroDiaMes);
        inicio.setDate(primeiroDiaMes.getDate() - primeiroDiaMes.getDay());

        const fim = new Date(ultimoDiaMes);
        fim.setDate(ultimoDiaMes.getDate() + (6 - ultimoDiaMes.getDay()));

        let cursor = new Date(inicio);

        while (cursor <= fim) {
            const semana: Date[] = [];

            for (let i = 0; i < 7; i++) {
                semana.push(new Date(cursor));
                cursor.setDate(cursor.getDate() + 1);
            }

            semanas.push(semana);
        }

        return semanas;
    });

    readonly currentWeek = computed(() =>
        this.weeks()[this.currentWeekIndex()]
    );

    readonly shiftsByDay = computed(() => {
        const map = new Map<number, PlantaoEscalaSimple[]>();

        for (let i = 0; i < 7; i++) {
            map.set(i, []);
        }

        const semana = this.currentWeek();
        if (!semana) return map;

        this.plantoes.forEach(shift => {
            const entradaKey = this.getDateKey(new Date(shift.horaEntrada));

            semana.forEach((dia, index) => {
                const diaKey = this.getDateKey(dia);

                if (entradaKey === diaKey) {
                    map.get(index)!.push(shift);
                }
            });
        });

        map.forEach(lista =>
            lista.sort(
                (a, b) =>
                    new Date(a.horaEntrada).getTime() -
                    new Date(b.horaEntrada).getTime()
            )
        );

        return map;
    });

    // Ações - semana
    previousWeek(): void {
        if (this.currentWeekIndex() > 0) {
            this.currentWeekIndex.update(v => v - 1);
        }
    }

    nextWeek(): void {
        if (this.currentWeekIndex() < this.weeks().length - 1) {
            this.currentWeekIndex.update(v => v + 1);
        }
    }

    // Ações popover
    openShiftDetails(
        event: MouseEvent,
        shift: PlantaoEscalaSimple
    ): void {
        if (!event) return;

        const MODAL_WIDTH = 440;
        const MODAL_HEIGHT = 160;
        const MARGIN = 16;

        const clickX = event.clientX;
        const clickY = event.clientY;

        const viewportWidth = window.innerWidth;
        const viewportHeight = window.innerHeight;

        let x = clickX;
        let y = clickY;

        if (clickX + MODAL_WIDTH + MARGIN > viewportWidth) {
            x = clickX - MODAL_WIDTH;
        }

        if (clickY + MODAL_HEIGHT + MARGIN > viewportHeight) {
            y = clickY - MODAL_HEIGHT;
        }

        this.popoverPosition.set({ x, y });
        this.selectedShift.set(shift);
        this.isPopoverVisible.set(true);
    }

    closeShiftDetails(): void {
        this.isPopoverVisible.set(false);
        this.selectedShift.set(null);
    }

    handleViewDetails(shiftId: number): void {
        this.router.navigate(['/plantao', shiftId]);
        this.closeShiftDetails();
    }

}