import { InstituicaoRequest } from "../../instituicoes/models/instituicao-request";
import { ProfissionalRequest } from "../../profissionais/models/profissional-request";

export interface CadastroRequest {

    email: string;
    senha: string;

    profissional?: ProfissionalRequest;
    instituicao?: InstituicaoRequest;
}