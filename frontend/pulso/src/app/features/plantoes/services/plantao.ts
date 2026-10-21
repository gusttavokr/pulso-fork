import { inject, Injectable } from "@angular/core";
import { PlantaoAPIService } from "./plantaoAPI";
import { AuthService } from "../../autenticacao/services/auth";
import { PlantaoFiltro } from "../models/plantao-filtro";
import { VisualizacaoTipo } from "../../../shared/enums/visualizacao-tipo";
import { PlantaoRequest } from "../models/plantao-request";
import { AlocacaoRequest } from "../models/alocacao-request";

@Injectable({ providedIn: 'root' })
export class PlantaoService {

    private readonly api = inject(PlantaoAPIService);
    private readonly auth = inject(AuthService);

    listarComContexto(
        filtros: PlantaoFiltro,
        visualizacao: VisualizacaoTipo
    ) {

        const params: PlantaoFiltro = { ...filtros };

        if (this.auth.isProfissional && visualizacao === 'PROFISSIONAL') {
            params.profissional = true;
            params.liderEscala = false;
        }

        if (this.auth.isLiderEscala && visualizacao === 'LIDER_ESCALA') {
            params.liderEscala = true;
            params.profissional = false;
        }

        if (this.auth.isInstituicao) {
            params.instituicao = true;
        }

        return this.api.listar(params);
    }

    buscarPorId(id: number) {
        return this.api.buscarPorId(id);
    }

    adicionar(payload: PlantaoRequest) {
        return this.api.adicionar(payload);
    }

    atualizar(id: number, payload: PlantaoRequest) {
        return this.api.atualizar(id, payload);
    }

    atualizarCombinacao(payload: AlocacaoRequest, adicionar: boolean) {
        return this.api.atualizarCombinacao(payload, adicionar);
    }

    alocar(payload: AlocacaoRequest) {
        return this.api.alocar(payload);
    }

    atualizarComentario(id: number, comentario: string) {
        return this.api.atualizarComentario(id, comentario);
    }
    
}