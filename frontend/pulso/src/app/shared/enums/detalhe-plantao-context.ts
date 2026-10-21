import { PlantaoFiltro } from "../../features/plantoes/models/plantao-filtro";
import { PlantaoVisualizacaoContexto } from "./plantao-visualizacao-contexto";

export interface DetalhePlantaoContext {
    origem: PlantaoVisualizacaoContexto;
    filtrosAtuais?: PlantaoFiltro;
}