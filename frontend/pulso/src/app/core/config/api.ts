import { Base } from 'primeng/base';
import { environment } from '../../../environments/environment';

export const API = {
    base: environment.apiUrl,

    auth: {
        base: `${environment.apiUrl}/usuarios`,
        login: `${environment.apiUrl}/usuarios/login`,
        cadastro: `${environment.apiUrl}/usuarios/cadastro`
    },

    instituicoes: {
        base: `${environment.apiUrl}/instituicoes`,
        status: `${environment.apiUrl}/instituicoes/status`
    },

    plantoes: {
        base: `${environment.apiUrl}/plantoes`,
        alocacao: `${environment.apiUrl}/plantoes/alocacao`
    },

    escalas: {
        base: `${environment.apiUrl}/escalas`,
        publicacao: `${environment.apiUrl}/escalas/publicacao`
    },

    profissionais: {
        base: `${environment.apiUrl}/profissionais`
    }
};