import { Especialidade } from "../../../shared/enums/especialidade";
import { TipoPix } from "../../../shared/enums/tipo-pix";
import { UF } from "../../../shared/enums/uf";

export interface ProfissionalRequest {

  nome: string;
  emails?: string[];
  telefone?: string;
  fotoPerfil?: string;

  anoFormacao: string;

  pix: string;
  tipoPix?: TipoPix;

  crmNumero: string;
  crms: UF[];
  
  dataNasc: string;

  especialidades: Especialidade[];

}