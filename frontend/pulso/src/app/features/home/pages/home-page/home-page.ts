import { Component, inject } from "@angular/core";
import { AuthService } from "../../../autenticacao/services/auth";
import { CommonModule } from "@angular/common";
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { CardPlantaoCobertoComponent } from "../../components/card-plantao-coberto/card-plantao-coberto";
import { CardPlantaoMarcadoComponent } from "../../components/card-plantao-marcado/card-plantao-marcado";
import { CardPlantaoPendenteComponent } from "../../components/card-plantao-pendente/card-plantao-pendente";
import { CardPlantaoPerdidoComponent } from "../../components/card-plantao-perdido/card-plantao-perdido";
import { CardProfissionaisAlocadosComponent } from "../../components/card-profissionais-alocados/card-profissionais-alocados";
import { CardProximoPlantaoComponent } from "../../components/card-proximo-plantao/card-proximo-plantao";
import { CardProgressoMensalComponent } from "../../components/card-progresso-mensal/card-progresso-mensal";

@Component({
    selector: 'app-home',
    imports: [
        CommonModule,
        BreadcrumbModule,
        CardPlantaoCobertoComponent,
        CardPlantaoMarcadoComponent,
        CardPlantaoPendenteComponent,
        CardPlantaoPerdidoComponent,
        CardProfissionaisAlocadosComponent,
        CardProximoPlantaoComponent,
        CardProgressoMensalComponent
    ],
    templateUrl: './home-page.html'
})
export class HomeComponent {

    readonly auth = inject(AuthService);

    home = { icon: 'pi pi-home' };
    items = [];
    nome = '';

    ngOnInit(): void {
        this.montarNome();
    }

    private montarNome(): void {
        const user = this.auth.user;

        if (!user) {
            this.nome = '';
            return;
        }

        const isProfissional = !!user.profissional;

        this.nome =
            (isProfissional ? 'Dr(a). ' : '') +
            (user.instituicao?.nome ?? user.profissional?.nome ?? '');
    }
}