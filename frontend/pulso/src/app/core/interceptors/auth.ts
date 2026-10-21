import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { Router } from "@angular/router";
import { AuthService } from "../../features/autenticacao/services/auth";

export function authIntercept(
    req: HttpRequest<unknown>, 
    next: HttpHandlerFn
): Observable<HttpEvent<unknown>> {

    if (
        req.url.includes('/login') ||
        req.url.includes('/cadastro')
    ) {
        return next(req);
    }

    const servicoUsuario = inject(AuthService);
    const router = inject(Router);

    // Não redirecionar se já estiver na página de cadastro
    const currentUrl = router.url;
    if (currentUrl.includes('/cadastro') || currentUrl.includes('/login')) {
        return next(req);
    }

    if (servicoUsuario.tokenExpired()) {
        router.navigate(['/login']); // Redirecionamento para a tela de login
        return new Observable<HttpEvent<unknown>>((observer) => {
            observer.complete();
        });
    }

    const token = sessionStorage.getItem('token');

    if (token) {
        const reqClonada = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
        return next(reqClonada);
    }

    // Se não tiver token, segue sem alterar
    return next(req);
}