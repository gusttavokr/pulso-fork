import { Component } from "@angular/core";
import { CardModule } from 'primeng/card';
import { AvatarModule } from 'primeng/avatar';

@Component({
    selector: 'app-card-plantao-marcado',
    imports: [
        CardModule,
        AvatarModule
    ],
    templateUrl: 'card-plantao-marcado.html'
})
export class CardPlantaoMarcadoComponent {
    plantaoMarcado = { valor: 5, descricao: 'Plantões marcados' }
}