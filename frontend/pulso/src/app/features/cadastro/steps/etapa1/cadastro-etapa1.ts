import { Component, inject, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router } from "@angular/router";
import { CadastroService } from "../../services/cadastro";

@Component({
    selector: 'app-cadastro-etapa1',
    standalone: true,
    imports: [CommonModule],
    templateUrl: `cadastro-etapa1.html`
})
export class CadastroEtapa1Component implements OnInit {

    readonly cadastroService = inject(CadastroService);
    readonly router = inject(Router);

    erroCadastro = "";

    profissional: 1 | 2 | null = null;

    ngOnInit() {
        this.cadastroService.setPasso(1);

        // restaura seleção ao voltar
        const tipo = this.cadastroService.getTipoUsuario();

        if (tipo === 'profissional') this.profissional = 1;
        if (tipo === 'instituicao') this.profissional = 2;
    }

    selecionarProfissional() {
        this.profissional = this.profissional === 1 ? null : 1;
        this.erroCadastro = "";
    }

    selecionarInstituicao() {
        this.profissional = this.profissional === 2 ? null : 2;
        this.erroCadastro = "";
    }

    proximo() {
        this.erroCadastro = "";

        if (!this.profissional) {
            this.erroCadastro = "Escolha uma opção antes de continuar.";
            return;
        }

        this.cadastroService.setDadosEtapa1({
            tipoUsuario: this.profissional === 1 ? 'profissional' : 'instituicao'
        });

        this.cadastroService.setPasso(2);
        this.router.navigate(['/cadastro/etapa2']);
    }
}