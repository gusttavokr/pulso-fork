import { Especialidade } from "../../../shared/enums/especialidade";
import { FormaPagamento } from "../../../shared/enums/forma-pagamento";
import { StatusEscala } from "../../../shared/enums/status-escala";
import { TipoTroca } from "../../../shared/enums/tipo-troca";
import { ProfissionalSimple } from "../../profissionais/models/profissional-simple";

export interface PlantaoResponse {
    id: number;
    especialidade: Especialidade;
    nomeInstituicao: string;
    horaEntrada: string;
    horaSaida: string;
    idLider: number;
    fotoLider: string;
    nomeLider: string;
    nomeSetor: string;
    enderecoInstituicao: string;
    comentario?: string;
    formaPagamento?: FormaPagamento;
    estimativaValor?: number;
    comentariotroca?: string;
    tipoTroca?: TipoTroca;
    idEscala: number;
    statusEscala: StatusEscala;
    quantidadeProfissionais: number;
    estimativaPagamento: string;
    profissionaisCombinados: ProfissionalSimple[]
    profissionaisConfirmados: ProfissionalSimple[]
    profissionaisTrocaDestino: ProfissionalSimple[]
}