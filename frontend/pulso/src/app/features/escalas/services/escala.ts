import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { EscalaFiltro } from "../models/escala-filtro";
import { Observable } from "rxjs";
import { EscalaSimple } from "../models/escala-simple";
import { API } from "../../../core/config/api";
import { EscalaResponse } from "../models/escala-response";
import { EscalaRequest } from "../models/escala-request";

@Injectable({
    providedIn: 'root'
})
export class EscalaAPIService {

    private readonly http = inject(HttpClient);

    listar(
        filtros?: EscalaFiltro
    ): Observable<EscalaSimple[]> {

        const params = this.buildParams(filtros);

        return this.http.get<EscalaSimple[]>(
            API.escalas.base,
            { params }
        );
    }

    buscarPorId(id: number): Observable<EscalaResponse> {
        return this.http.get<EscalaResponse>(
            `${API.escalas.base}/${id}`
        );
    }

    adicionar(req: EscalaRequest): Observable<EscalaResponse> {
        return this.http.post<EscalaResponse>(
            API.escalas.base,
            req
        );
    }

    atualizar(
        id: number,
        req: EscalaRequest
    ): Observable<EscalaResponse> {
        return this.http.put<EscalaResponse>(
            `${API.escalas.base}/${id}`,
            req
        );
    }

    remover(id: number): Observable<void> {
        return this.http.delete<void>(
            `${API.escalas.base}/${id}`
        );
    }

    publicar(id: number): Observable<EscalaResponse> {
        return this.http.patch<EscalaResponse>(
            `${API.escalas.base}/${id}/publicacao`,
            null,
            {
                params: {
                    publicada: true
                }
            }
        );
    }

    private buildParams(
        filtros?: EscalaFiltro
    ): HttpParams {

        let params = new HttpParams();

        if (!filtros) return params;

        if (filtros.especialidade) {
            params = params.set('especialidade', filtros.especialidade);
        }

        if (filtros.mesReferencia) {
            params = params.set('mesReferencia', filtros.mesReferencia);
        }

        if (filtros.status) {
            params = params.set('status', filtros.status);
        }

        if (filtros.liderEscala !== undefined) {
            params = params.set('liderEscala', String(filtros.liderEscala));
        }

        if (filtros.instituicao !== undefined) {
            params = params.set('instituicao', String(filtros.instituicao));
        }

        return params;
    }

}