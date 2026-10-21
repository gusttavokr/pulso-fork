import { StatusEscala } from "../../../shared/enums/status-escala";

export interface EscalaSimple {
    id: number;
    nome: string;
    mesReferencia: string; // YYYY-MM-DD
    status: StatusEscala;

    dataCriacao: string;      // ISO DateTime
    dataAtualizacao: string;  // ISO DateTime

    nomeLider: string;
    fotoLider: string | null;
}