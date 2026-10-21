export enum CategoriaFiltro {
    NOVO_PLANTAO = 'NOVO_PLANTAO',
    TROCA = 'TROCA',
    REPASSE = 'REPASSE'
}

export const CATEGORIA_LABEL: Record<CategoriaFiltro, string> = {
    [CategoriaFiltro.NOVO_PLANTAO]: 'Novo plantão',
    [CategoriaFiltro.TROCA]: 'Troca',
    [CategoriaFiltro.REPASSE]: 'Repasse'
};