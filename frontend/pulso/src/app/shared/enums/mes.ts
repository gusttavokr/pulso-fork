export enum Mes {
    JANEIRO = 1,
    FEVEREIRO = 2,
    MARCO = 3,
    ABRIL = 4,
    MAIO = 5,
    JUNHO = 6,
    JULHO = 7,
    AGOSTO = 8,
    SETEMBRO = 9,
    OUTUBRO = 10,
    NOVEMBRO = 11,
    DEZEMBRO = 12
}

export const MES_LABEL: Record<Mes, string> = {
    [Mes.JANEIRO]: 'Janeiro',
    [Mes.FEVEREIRO]: 'Fevereiro',
    [Mes.MARCO]: 'Março',
    [Mes.ABRIL]: 'Abril',
    [Mes.MAIO]: 'Maio',
    [Mes.JUNHO]: 'Junho',
    [Mes.JULHO]: 'Julho',
    [Mes.AGOSTO]: 'Agosto',
    [Mes.SETEMBRO]: 'Setembro',
    [Mes.OUTUBRO]: 'Outubro',
    [Mes.NOVEMBRO]: 'Novembro',
    [Mes.DEZEMBRO]: 'Dezembro'
};