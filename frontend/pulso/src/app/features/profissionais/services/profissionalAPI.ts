import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProfissionalEstatistica } from "../models/profissional-estatistica";
import { API } from "../../../core/config/api";
import { ProfissionalRequest } from "../models/profissional-request";
import { ProfissionalResponse } from "../models/profissional-response";

@Injectable({ providedIn: 'root' })
export class ProfissionalAPIService {

    private readonly http = inject(HttpClient);

    listar(
        idPlantao?: number
    ): Observable<ProfissionalEstatistica[]> {

        const params = this.buildParams(idPlantao);

        return this.http.get<ProfissionalEstatistica[]>(
            API.profissionais.base,
            { params }
        );
    }

    adicionar(
        req: ProfissionalRequest
    ): Observable<ProfissionalResponse> {

        return this.http.post<ProfissionalResponse>(
            API.profissionais.base,
            req
        );
    }

    private buildParams(
        idPlantao?: number
    ): HttpParams {

        let params = new HttpParams();

        if (!idPlantao) return params;

        params = params.set('idPlantao', idPlantao);

        return params;
    }
}