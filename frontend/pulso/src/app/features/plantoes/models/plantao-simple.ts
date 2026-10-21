import { Especialidade } from "../../../shared/enums/especialidade";

export interface PlantaoSimple {
  id: number;

  nomeInstituicao: string;
  fotoLider: string | null;
  nomeLider: string;
  especialidade: Especialidade;

  horaEntrada: string; // ISO DateTime
  horaSaida: string;   // ISO DateTime

  estimativaValor: number;

  ehNovoPlantao: boolean;
  ehTroca: boolean;
  ehRepasse: boolean;
}