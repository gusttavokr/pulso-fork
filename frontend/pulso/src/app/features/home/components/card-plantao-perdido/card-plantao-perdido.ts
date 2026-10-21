import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { AvatarModule } from 'primeng/avatar';

@Component({
  selector: 'app-card-plantao-perdido',
  imports: [
    CardModule,
    AvatarModule
  ],
  templateUrl: 'card-plantao-perdido.html'
})
export class CardPlantaoPerdidoComponent {
  plantaoPerdido = { valor: 1, descricao: 'Plantões perdidos' }
}