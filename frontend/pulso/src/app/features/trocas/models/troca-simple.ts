import { StatusTroca } from "../../../shared/enums/status-troca";
import { TipoTroca } from "../../../shared/enums/tipo-troca";

export interface TrocaSimple {
  id: number;
  comentario: string;
  tipo: TipoTroca;
  status: StatusTroca;
}