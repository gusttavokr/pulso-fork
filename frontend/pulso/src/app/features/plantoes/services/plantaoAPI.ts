import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PlantaoFiltro } from "../models/plantao-filtro";
import { Observable } from "rxjs";
import { PlantaoSimple } from "../models/plantao-simple";
import { API } from "../../../core/config/api";
import { PlantaoResponse } from "../models/plantao-response";
import { AlocacaoRequest } from "../models/alocacao-request";
import { PlantaoRequest } from "../models/plantao-request";

@Injectable({
    providedIn: 'root'
})
export class PlantaoAPIService {

    private readonly http = inject(HttpClient);

    // Listagem
    listar(
        filtros?: PlantaoFiltro
    ): Observable<PlantaoSimple[]> {

        const params = this.buildParams(filtros);

        return this.http.get<PlantaoSimple[]>(
            API.plantoes.base,
            { params }
        )
    }

    // Datalhe
    buscarPorId(id: number): Observable<PlantaoResponse> {

        return this.http.get<PlantaoResponse>(
            `${API.plantoes.base}/${id}`
        );

    }

    // Alocação
    alocar(
        req: AlocacaoRequest
    ): Observable<PlantaoResponse> {

        return this.http.post<PlantaoResponse>(
            `${API.plantoes.alocacao}`,
            req
        );

    }

    atualizarCombinacao(
        req: AlocacaoRequest,
        adicionar: boolean
    ): Observable<PlantaoResponse> {

        return this.http.post<PlantaoResponse>(
            API.plantoes.alocacao,
            req,
            {
                params: {
                    adicionar
                }
            }
        );

    }

    // CRUD
    adicionar(
        req: PlantaoRequest
    ): Observable<PlantaoResponse> {

        return this.http.post<PlantaoResponse>(
            API.plantoes.base,
            req
        );

    }

    atualizar(
        id: number,
        req: PlantaoRequest
    ): Observable<PlantaoResponse> {

        return this.http.put<PlantaoResponse>(
            `${API.plantoes.base}/${id}`,
            req
        );

    }

    atualizarComentario(
        idPlantao: number,
        comentario: string
    ): Observable<PlantaoResponse> {

        return this.http.patch<PlantaoResponse>(
            `${API.plantoes.base}/${idPlantao}/comentario`,
            null,
            {
                params: {
                    comentario
                }
            }
        );

    }

    // Helpers
    private buildParams(
        filtros?: PlantaoFiltro
    ): HttpParams {

        let params = new HttpParams();

        if (!filtros) return params;

        // simples
        if (filtros.abertos !== undefined) {
            params = params.set('abertos', String(filtros.abertos));
        }

        if (filtros.data) {
            params = params.set('data', filtros.data);
        }

        if (filtros.especialidade) {
            params = params.set('especialidade', filtros.especialidade);
        }

        if (filtros.precoMin !== undefined && filtros.precoMin !== null) {
            params = params.set('precoMin', String(filtros.precoMin));
        }

        if (filtros.precoMax !== undefined && filtros.precoMax !== null) {
            params = params.set('precoMax', String(filtros.precoMax));
        }

        // arrays (⚠️ importante usar append)
        if (filtros.horariosInicio?.length) {
            filtros.horariosInicio.forEach(h => {
                params = params.append('horariosInicio', h);
            });
        }

        if (filtros.horariosFim?.length) {
            filtros.horariosFim.forEach(h => {
                params = params.append('horariosFim', h);
            });
        }

        // categorias (booleans)
        if (filtros.ehNovoPlantao !== undefined) {
            params = params.set('ehNovoPlantao', String(filtros.ehNovoPlantao));
        }

        if (filtros.ehRepasse !== undefined) {
            params = params.set('ehRepasse', String(filtros.ehRepasse));
        }

        if (filtros.ehTroca !== undefined) {
            params = params.set('ehTroca', String(filtros.ehTroca));
        }

        // contexto
        if (filtros.profissional !== undefined) {
            params = params.set('profissional', String(filtros.profissional));
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