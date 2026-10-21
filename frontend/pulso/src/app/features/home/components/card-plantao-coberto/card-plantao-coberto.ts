import { Component } from "@angular/core";
import { CardModule } from 'primeng/card';
import { AvatarModule } from 'primeng/avatar';

@Component({
    selector: 'app-card-plantao-coberto',
    imports: [
        CardModule,
        AvatarModule
    ],
    templateUrl: 'card-plantao-coberto.html'
})
export class CardPlantaoCobertoComponent {
    plantaoCoberto = { valor: 23, descricao: 'Plantões cobertos' }
}