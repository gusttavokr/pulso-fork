import { Especialidade } from "../../../shared/enums/especialidade";
import { TipoPix } from "../../../shared/enums/tipo-pix";
import { EscalaSimple } from "../../escalas/models/escala-simple";
import { InstituicaoSimple } from "../../instituicoes/models/instituicao-simple";
import { PlantaoSimple } from "../../plantoes/models/plantao-simple";
import { TrocaSimple } from "../../trocas/models/troca-simple";
import { UsuarioSimple } from "../../usuarios/models/usuario-simple";

export interface ProfissionalResponse {

  id: number;
  nome: string;
  emails: string[];
  telefone?: string;
  fotoPerfil?: string;

  anoFormacao: string;
  pix: string;
  tipoPix: TipoPix;

  crmsFormatados: string[];

  dataNasc: string;
  especialidades: Especialidade[];
  ehLiderEscala: boolean;

  usuario: UsuarioSimple;

  instituicoesClt: InstituicaoSimple[];
  instituicoesPj: InstituicaoSimple[];

  escalas: EscalaSimple[];

  plantoesCombinados: PlantaoSimple[];
  plantoesConfirmados: PlantaoSimple[];

  trocasOrigem: TrocaSimple[];
  trocasDestino: TrocaSimple[];

}