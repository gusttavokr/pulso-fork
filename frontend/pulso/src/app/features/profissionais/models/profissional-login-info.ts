import { Especialidade } from "../../../shared/enums/especialidade";

export interface ProfissionalLoginInfo {
    id: number;
    nome: string;
    fotoPerfil?: string | null;
    especialidades: Especialidade[];
}