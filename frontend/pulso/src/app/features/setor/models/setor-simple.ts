import { Especialidade } from "../../../shared/enums/especialidade";

export interface SetorSimple {
  id: number;
  nome: string;
  especialidade: Especialidade;
}