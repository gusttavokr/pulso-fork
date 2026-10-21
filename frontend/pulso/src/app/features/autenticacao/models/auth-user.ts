import { TipoUsuario } from "../../../shared/enums/tipo-usuario";
import { InstituicaoLoginInfo } from "../../instituicoes/models/instituicao-login-info";
import { ProfissionalLoginInfo } from "../../profissionais/models/profissional-login-info";

export interface AuthUser {
  id: number;

  tipo: TipoUsuario;

  roles: string[];

  instituicao?: InstituicaoLoginInfo;

  profissional?: ProfissionalLoginInfo;
}