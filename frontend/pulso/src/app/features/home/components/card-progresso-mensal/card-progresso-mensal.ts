import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';

@Component({
    selector: 'app-card-progresso-mensal',
    imports: [CardModule],
    templateUrl: 'card-progresso-mensal.html'
})
export class CardProgressoMensalComponent {
    cargaHoraria: number = 25;
    horasConcluidas: number = 5;

    progressoSecundario: number = 60;
    horasPendentes: number = 10;

    getCargaHoraria(): number {
        return this.cargaHoraria;
    }

    getHorasConcluidas(): number {
        return this.horasConcluidas;
    }
    getHorasPendentes(): number {
        return this.horasPendentes;
    }

    getProgressoSecundario(): number {
        return this.progressoSecundario;
    }

}