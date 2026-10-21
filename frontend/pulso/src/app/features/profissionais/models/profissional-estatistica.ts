export interface EstatisticaPlantoesAno {
    ano: number;
    meses: {
        mes: number;          // 1 = Jan, 12 = Dez
        quantidade: number;
    }[];
}

export interface ProfissionalEstatistica {
    id: number;
    foto: string;
    nome: string;
    emails: string[];
    telefone: string;
    estatisticasPlantoes: EstatisticaPlantoesAno;
}