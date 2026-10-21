import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginRequest } from "../models/login-request";
import { LoginResponse } from "../models/login-response";
import { CadastroRequest } from "../models/cadastro-request";
import { UsuarioResponse } from "../../usuarios/models/usuario-response";
import { API } from "../../../core/config/api";

@Injectable({
    providedIn: 'root'
})
export class AuthApiService {

    private http = inject(HttpClient);

    login(req: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(
            API.auth.login,
            req
        );
    }

    cadastrar(req: CadastroRequest): Observable<UsuarioResponse> {
        return this.http.post<UsuarioResponse>(
            API.auth.cadastro,
            req
        );
    }
}