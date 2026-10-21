import { Especialidade } from "../../../shared/enums/especialidade";
import { ProfissionalSimple } from "../../profissionais/models/profissional-simple";

export interface PlantaoEscalaSimple {
    id: number;
    
    nomeInstituicao: string;
    especialidade: Especialidade;
    qtdProfissionais: number;
    estimativaValor: number;

    horaEntrada: string;
    horaSaida: string;

    profissionaisAlocados: ProfissionalSimple[];
}