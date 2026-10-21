import { FormaPagamento } from "../../../shared/enums/forma-pagamento";

export interface PlantaoRequest {
    horaEntrada?: string;
    horaSaida?: string;
    estimativaValor?: number;
    formaPagamento?: FormaPagamento;
    estimativaPagamento?: string;
    comentario?: string;
    quantidadeProfissionais?: number;
    escalaId?: number;
}