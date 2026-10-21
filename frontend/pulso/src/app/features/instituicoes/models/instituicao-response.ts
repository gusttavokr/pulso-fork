import { ProfissionalSimple } from "../../profissionais/models/profissional-simple";
import { SetorSimple } from "../../setor/models/setor-simple";
import { UsuarioSimple } from "../../usuarios/models/usuario-simple";

export interface InstituicaoResponse {

  id: number;

  nome: string;

  emails: string[];

  telefone: string;

  fotoPerfil: string;

  endereco: string;

  cnpj: string;

  exigeVinculo: boolean;

  usuario: UsuarioSimple;

  setores: SetorSimple[];

  profissionaisClt: ProfissionalSimple[];

  profissionaisPj: ProfissionalSimple[];

}