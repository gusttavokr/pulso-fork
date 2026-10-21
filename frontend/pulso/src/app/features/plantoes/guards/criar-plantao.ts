import { inject, Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "../../autenticacao/services/auth";
import { CriacaoPlantaoContextService } from "../services/criacao-plantao-context";

@Injectable({ providedIn: 'root' })
export class CriarPlantaoGuard implements CanActivate {

    private readonly auth = inject(AuthService);
    private readonly router = inject(Router);
    private readonly contexto = inject(CriacaoPlantaoContextService);

    canActivate(): boolean {

        if (!this.auth.hasRole('LIDER_ESCALA')) {
            this.router.navigate(['/acesso-negado']);
            return false;
        }

        if (!this.contexto.escalaId) {
            this.router.navigate(['/escala']);
            return false;
        }

        return true;
    }
}