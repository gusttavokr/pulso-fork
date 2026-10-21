export function calcularStatusTempo(horaEntrada: string): 'atrasado' | 'proximo' | 'distante' {
    const agora = new Date();
    const inicio = new Date(horaEntrada);

    if (inicio < agora) return 'atrasado';

    const diffDias =
        (inicio.getTime() - agora.getTime()) / (1000 * 60 * 60 * 24);

    if (diffDias <= 2) return 'proximo';

    return 'distante';
}

export function obterCorAlerta(status: 'atrasado' | 'proximo' | 'distante'): string {
    switch (status) {
        case 'atrasado': return '#FF4141';
        case 'proximo': return '#FFB200';
        default: return '#21C694';
    }
}

export function calcularDuracaoHoras(inicio: string, fim: string): number {
    const diffMs = new Date(fim).getTime() - new Date(inicio).getTime();
    return Math.round(diffMs / (1000 * 60 * 60));
}

export function isPlantaoAberto(context: any): boolean {
    return context === 'PLANTOES_ABERTOS';
}

export function isMeusPlantoes(context: any): boolean {
    return context === 'MEUS_PLANTOES';
}

export function escalaPublicada(status: any): boolean {
    return status === 'PUBLICADA';
}