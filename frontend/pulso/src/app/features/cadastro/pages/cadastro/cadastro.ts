import { Component, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { CadastroService } from '../../services/cadastro';

@Component({
    selector: 'app-cadastro',
    imports: [CommonModule, RouterOutlet],
    templateUrl: './cadastro.html',
})
export class Cadastro {

    constructor(private location: Location) { }
    readonly _cadastroService = inject(CadastroService);

    voltar() {
        this.location.back();
        this._cadastroService.voltarPasso()
    }

    resgatarPasso(): number {
        return this._cadastroService.getPasso();
    }

    regatarTipoUsuario(): 'profissional' | 'instituicao' | null {
        return this._cadastroService.getTipoUsuario();
    }
}