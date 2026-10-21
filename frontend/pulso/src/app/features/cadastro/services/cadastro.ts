import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DadosEtapa1 } from '../models/cadastro-etapa1';
import { DadosEtapa2 } from '../models/cadastro-etapa2';
import { DadosEtapa3 } from '../models/cadastro-etapa3';
import { ProfissionalRequest } from '../../profissionais/models/profissional-request';
import { InstituicaoRequest } from '../../instituicoes/models/instituicao-request';
import { CadastroRequest } from '../../autenticacao/models/cadastro-request';
import { AuthApiService } from '../../autenticacao/services/auth-api';

@Injectable({
    providedIn: 'root'
})
export class CadastroService {

    private readonly usuarioService = inject(AuthApiService);

    /* ================= STATE ================= */

    private passoAtual = 1;
    private fotoBase64?: string;

    private dadosEtapa1: DadosEtapa1 = { tipoUsuario: null };
    private dadosEtapa2: DadosEtapa2 | null = null;
    private dadosEtapa3: DadosEtapa3 | null = null;

    /* ================= PASSOS ================= */

    getPasso() {
        return this.passoAtual;
    }

    setPasso(passo: number) {
        this.passoAtual = passo;
    }

    avancarPasso() {
        this.passoAtual++;
    }

    voltarPasso() {
        if (this.passoAtual > 1) this.passoAtual--;
    }

    /* ================= ETAPAS ================= */

    setDadosEtapa1(dados: DadosEtapa1) {
        this.dadosEtapa1 = dados;
    }

    setDadosEtapa2(dados: DadosEtapa2) {
        this.dadosEtapa2 = dados;
    }

    setDadosEtapa3(dados: DadosEtapa3) {
        this.dadosEtapa3 = dados;
    }

    getDadosEtapa2() {
        return this.dadosEtapa2;
    }

    getTipoUsuario() {
        return this.dadosEtapa1.tipoUsuario;
    }

    isProfissional() {
        return this.dadosEtapa1.tipoUsuario === 'profissional';
    }

    isInstituicao() {
        return this.dadosEtapa1.tipoUsuario === 'instituicao';
    }

    onFotoSelecionada(event: Event) {
        const input = event.target as HTMLInputElement;

        if (!input.files || input.files.length === 0) return;

        const file = input.files[0];

        const reader = new FileReader();

        reader.onload = () => {
            this.fotoBase64 = reader.result as string;
        };

        reader.readAsDataURL(file);
    }

    /* ================= SUBMIT FINAL ================= */

    finalizarCadastro(): Observable<any> {
        if (!this.dadosEtapa2 || !this.dadosEtapa3) {
            throw new Error('Dados incompletos para finalizar cadastro');
        }

        let profissional: ProfissionalRequest | undefined;
        let instituicao: InstituicaoRequest | undefined;

        if (this.isProfissional()) {
            profissional = this.buildProfissionalRequest();
        }

        if (this.isInstituicao()) {
            instituicao = this.buildInstituicaoRequest();
        }

        const cadastroRequest: CadastroRequest = {
            email: this.dadosEtapa2.email,
            senha: this.dadosEtapa2.senha,
            profissional,
            instituicao
        };

        return this.usuarioService.cadastrar(cadastroRequest);
    }

    /* ================= BUILD REQUESTS ================= */

    private buildProfissionalRequest(): ProfissionalRequest {
        const etapa2 = this.dadosEtapa2!;
        const etapa3 = this.dadosEtapa3!;

        const crms = Array.isArray(etapa3.crms)
            ? etapa3.crms
            : etapa3.crms
                ? [etapa3.crms]
                : [];

        const especialidades = Array.isArray(etapa3.especialidades)
            ? etapa3.especialidades
            : etapa3.especialidades
                ? [etapa3.especialidades]
                : [];

        return {
            nome: etapa2.nome,
            telefone: etapa2.telefone,
            dataNasc: etapa2.dataNascimento!,
            crmNumero: etapa3.crmNumero!,
            crms,
            anoFormacao: etapa3.dataFormacao!,
            especialidades,
            tipoPix: etapa3.tipoPix,
            pix: etapa3.chavePix!
        };
    }

    private buildInstituicaoRequest(): InstituicaoRequest {
        const etapa2 = this.dadosEtapa2!;
        const etapa3 = this.dadosEtapa3!;

        const endereco = `${etapa3.enderecoCompleto}`;

        return {
            nome: etapa2.nome,
            telefone: etapa2.telefone,
            endereco,
            cnpj: etapa2.cnpj!,
            exigeVinculo: true,
            fotoPerfil: etapa3.fotoPerfil!
        };
    }

    /* ================= RESET ================= */

    reset() {
        this.passoAtual = 1;
        this.dadosEtapa1 = { tipoUsuario: null };
        this.dadosEtapa2 = null;
        this.dadosEtapa3 = null;
    }
}