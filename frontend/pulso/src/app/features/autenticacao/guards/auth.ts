import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "../services/auth";

export const autenticacaoGuard: CanActivateFn = () => {
    const usuarioService = inject(AuthService);
    const router = inject(Router);

    if (usuarioService.isAuthenticated()) {
        return true;
    }

    return router.createUrlTree(['/login']);
};