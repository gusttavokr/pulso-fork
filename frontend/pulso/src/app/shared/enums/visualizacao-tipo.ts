export enum VisualizacaoTipo {
    LIDER_ESCALA = 'LIDER_ESCALA',
    PROFISSIONAL = 'PROFISSIONAL'
}

export const VISUALIZACAO_CONFIG = {
    [VisualizacaoTipo.LIDER_ESCALA]: {
        label: 'Líder de escala',
        icon: 'leader'
    },
    [VisualizacaoTipo.PROFISSIONAL]: {
        label: 'Profissional',
        icon: 'professional'
    }
} as const;