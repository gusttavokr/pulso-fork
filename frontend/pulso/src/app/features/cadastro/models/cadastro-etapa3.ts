import { Especialidade } from "../../../shared/enums/especialidade";
import { TipoPix } from "../../../shared/enums/tipo-pix";
import { UF } from "../../../shared/enums/uf";

export interface DadosEtapa3 {
    // PROFISSIONAL
    crmNumero?: string;
    crms?: UF[];
    dataFormacao?: string;
    especialidades?: Especialidade[];
    tipoPix?: TipoPix;
    chavePix?: string;

    // INSTITUIÇÃO
    enderecoCompleto?: String;
    fotoPerfil?: string;
}