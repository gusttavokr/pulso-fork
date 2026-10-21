import { Injectable, inject } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";

import { InstituicaoRequest } from "../models/instituicao-request";
import { InstituicaoResponse } from "../models/instituicao-response";
import { InstituicaoFiltro } from "../models/instituicao-filtro";
import { InstituicaoStatusResponse } from "../models/instituicao-status-response";
import { API } from "../../../core/config/api";

@Injectable({
    providedIn: 'root'
})
export class InstituicaoService {

    private readonly http = inject(HttpClient);

    // CRUD
    criarInstituicao(
        req: InstituicaoRequest
    ): Observable<InstituicaoResponse> {

        return this.http.post<InstituicaoResponse>(
            API.instituicoes.base,
            req
        );

    }

    listar(
        filtros?: InstituicaoFiltro
    ): Observable<InstituicaoResponse[]> {

        const params = this.buildParams(filtros);

        return this.http.get<InstituicaoResponse[]>(
            API.instituicoes.base,
            { params }
        );

    }

    obterStatus(): Observable<InstituicaoStatusResponse> {

        return this.http.get<InstituicaoStatusResponse>(
            API.instituicoes.status
        );

    }

    // Helpers
    private buildParams(
        filtros?: InstituicaoFiltro
    ): HttpParams {

        let params = new HttpParams();

        if (!filtros) return params;

        Object.entries(filtros).forEach(([key, value]) => {

            if (value !== undefined && value !== null && value !== '') {

                params = params.set(key, String(value));

            }

        });

        return params;

    }

}