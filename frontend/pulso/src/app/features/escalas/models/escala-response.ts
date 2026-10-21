import { Especialidade } from "../../../shared/enums/especialidade";
import { StatusEscala } from "../../../shared/enums/status-escala";
import { PlantaoEscalaSimple } from "../../plantoes/models/plantao-escala-simple";

export interface EscalaResponse {
    id: number,
    nome: string,
    nomeInstituicao: string,
    nomeLider: string,
    fotoLider: string,
    nomeSetor: string,
    especialidadeSetor: Especialidade,
    mesReferencia: string,
    status: StatusEscala,
    plantoes: PlantaoEscalaSimple[],
}