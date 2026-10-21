import { InstituicaoSimple } from "../../instituicoes/models/instituicao-simple";
import { ProfissionalSimple } from "../../profissionais/models/profissional-simple";

export interface UsuarioResponse {

    id: number;

    email: string;

    ativo: boolean;

    ultimoLogin: string;   // ISO Date

    criadoEm: string;      // ISO Date

    atualizadoEm: string;  // ISO Date

    instituicao?: InstituicaoSimple;

    profissional?: ProfissionalSimple;

}