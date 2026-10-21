import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { AvatarModule } from 'primeng/avatar';

@Component({
  selector: 'app-card-plantao-pendente',
  imports: [
    CardModule,
    AvatarModule
  ],
  templateUrl: 'card-plantao-pendente.html'
})
export class CardPlantaoPendenteComponent {
  plantaoPendente = { valor: 1, descricao: 'Plantões pendentes' }
}