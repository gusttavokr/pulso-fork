import { inject, Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router } from "@angular/router";
import { AuthService } from "../../autenticacao/services/auth";
import { PlantaoService } from "../services/plantao";
import { catchError, map, Observable, of } from "rxjs";
import { StatusEscala } from "../../../shared/enums/status-escala";

@Injectable({ providedIn: 'root' })
export class AtualizarPlantaoGuard implements CanActivate {

    private readonly auth = inject(AuthService);
    private readonly router = inject(Router);
    private readonly plantaoService = inject(PlantaoService);

    canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {

        if (!this.auth.hasRole('LIDER_ESCALA')) {
            this.router.navigate(['/acesso-negado']);
            return of(false);
        }

        const idPlantao = Number(route.paramMap.get('id'));

        if (!idPlantao) {
            this.router.navigate(['/plantao']);
            return of(false);
        }

        return this.plantaoService.buscarPorId(idPlantao).pipe(
            map(plantao => {

                const idProfissionalLogado = this.auth.user?.profissional?.id;

                if (plantao.idLider !== idProfissionalLogado) {
                    this.router.navigate(['/acesso-negado']);
                    return false;
                }

                if (plantao.statusEscala === StatusEscala.PUBLICADA) {
                    this.router.navigate(['/plantao']);
                    return false;
                }

                return true;
            }),
            catchError(() => {
                this.router.navigate(['/plantao']);
                return of(false);
            })
        );
    }
}