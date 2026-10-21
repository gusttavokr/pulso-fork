import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { jwtDecode, JwtPayload } from 'jwt-decode';
import { LoginResponse } from "../models/login-response";
import { AuthUser } from "../models/auth-user";
import { TipoUsuario } from "../../../shared/enums/tipo-usuario";
import { Especialidade, ESPECIALIDADE_LABEL } from "../../../shared/enums/especialidade";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    // Storage keys
    private readonly TOKEN_KEY = 'token';
    private readonly USER_KEY = 'auth_user';

    // User state
    private userSubject = new BehaviorSubject<AuthUser | null>(this.loadUser());

    user$ = this.userSubject.asObservable();

    get user(): AuthUser | null {
        return this.userSubject.value;
    }

    // User helpers
    get isProfissional(): boolean {
        return this.user?.tipo === TipoUsuario.PROFISSIONAL;
    }

    get isLiderEscala(): boolean {
        return this.hasRole('LIDER_ESCALA');
    }

    get isInstituicao(): boolean {
        return this.user?.tipo === TipoUsuario.INSTITUICAO;
    }

    hasRole(role: string): boolean {
        const user = this.userSubject.value;
        return user ? user.roles.includes(role) : false;
    }

    get nome(): string {

        const user = this.user;

        return user?.profissional?.nome
            || user?.instituicao?.nome
            || 'Usuário';

    }

    get fotoPerfil(): string {

        const user = this.user;

        return user?.profissional?.fotoPerfil
            || user?.instituicao?.fotoPerfil
            || 'https://lades.cnat.ifrn.edu.br/wp-content/uploads/2024/12/Professor-Marcelo.png';

    }

    get especialidade(): string {

        const especialidadesUsuario =
            this.user?.profissional?.especialidades;

        if (!especialidadesUsuario?.length) return '';

        const codigo = especialidadesUsuario[0] as Especialidade;

        return ESPECIALIDADE_LABEL[codigo];

    }

    // Auth actions
    login(res: LoginResponse) {

        sessionStorage.setItem(this.TOKEN_KEY, res.token);

        const user: AuthUser = {
            id: res.id,
            tipo: res.tipo,
            roles: res.roles,
            instituicao: res.instituicao,
            profissional: res.profissional
        };

        sessionStorage.setItem(this.USER_KEY, JSON.stringify(user));

        this.userSubject.next(user);
    }

    logout() {
        sessionStorage.clear();
        this.userSubject.next(null);
    }

    isAuthenticated(): boolean {
        const token = this.getToken();
        return !!token && !this.tokenExpired();
    }

    // Token helpers
    getToken(): string | null {
        return sessionStorage.getItem(this.TOKEN_KEY);
    }

    tokenExpired(): boolean {

        const token = this.getToken();
        if (!token) return true;

        try {

            const decoded = jwtDecode<JwtPayload>(token);

            return (decoded.exp ?? 0) * 1000 < Date.now();

        } catch {

            return true;

        }

    }

    // Private helpers
    private loadUser(): AuthUser | null {

        const raw = sessionStorage.getItem(this.USER_KEY);

        return raw ? JSON.parse(raw) : null;

    }

}