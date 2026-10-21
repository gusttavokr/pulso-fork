import { Component, inject, OnInit, signal } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router } from "@angular/router";
import {
    FormControl,
    FormGroup,
    Validators,
    ReactiveFormsModule
} from "@angular/forms";

import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from "primeng/inputtext";
import { DatePickerModule } from "primeng/datepicker";
import { InputNumberModule } from "primeng/inputnumber";
import { SelectModule } from "primeng/select";
import { CadastroService } from "../../services/cadastro";
import { TipoPix } from "../../../../shared/enums/tipo-pix";
import { Especialidade } from "../../../../shared/enums/especialidade";
import { UF } from "../../../../shared/enums/uf";
import { DadosEtapa3 } from "../../models/cadastro-etapa3";
import { enumToOptions } from "../../../../shared/utils/enum-options";
import { FeedbackModalComponent } from "../../../../shared/components/modals/feedback-modal/feedback-modal";

@Component({
    selector: 'app-cadastro-etapa3',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        CommonModule,
        FileUploadModule,
        InputTextModule,
        DatePickerModule,
        InputNumberModule,
        SelectModule,
        FeedbackModalComponent
    ],
    templateUrl: 'cadastro-etapa3.html'
})
export class CadastroEtapa3Component implements OnInit {

    /* =====================================================
    DEPENDÊNCIAS
    ===================================================== */

    private readonly cadastroService = inject(CadastroService);
    private readonly router = inject(Router);


    /* =====================================================
    ESTADO DO COMPONENTE
    ===================================================== */

    erroCadastro = '';
    tipoUsuario: 'profissional' | 'instituicao' | null = null;

    modalSucesso = signal<boolean>(false);
    modalErro = signal<boolean>(false);

    fotoBase64: string = 'default.png';

    arquivoSelecionado: File | null = null;
    previewImagem: string | null = null;


    /* =====================================================
    SIGNALS (dados das etapas anteriores)
    ===================================================== */

    nome = signal<string | null>(null);
    email = signal<string | null>(null);
    telefone = signal<string | null>(null);


    /* =====================================================
    LISTAS (selects)
    ===================================================== */

    estadosBrasil = enumToOptions(UF);
    tiposPix = enumToOptions(TipoPix);
    especialidades = enumToOptions(Especialidade);


    /* =====================================================
    FORMULÁRIO
    ===================================================== */

    form = new FormGroup({

        /* PROFISSIONAL */

        crm: new FormControl<string | null>(null),
        estadoCrm: new FormControl<UF[]>([]),
        dataFormacao: new FormControl<string>(''),
        especialidade: new FormControl<Especialidade[]>([]),
        tipoPix: new FormControl<TipoPix | null>(null),
        chavePix: new FormControl<string>(''),

        /* INSTITUIÇÃO */

        enderecoCompleto: new FormControl<string>(''),
        fotoPerfil: new FormControl<File | null>(null)

    });


    /* =====================================================
    CICLO DE VIDA
    ===================================================== */

    ngOnInit() {

        this.tipoUsuario = this.cadastroService.getTipoUsuario();

        if (!this.tipoUsuario) {
            this.router.navigate(['/cadastro/etapa1']);
            return;
        }

        this.configurarValidacoes();

        if (this.tipoUsuario === 'instituicao') {
            this.carregarDadosEtapa2();
        }

    }


    /* =====================================================
    CONFIGURAÇÃO DE VALIDAÇÕES
    ===================================================== */

    private configurarValidacoes() {

        if (this.tipoUsuario === 'profissional') {

            this.form.get('crm')?.setValidators([
                Validators.required,
                Validators.min(9999),
                Validators.max(99999)
            ]);

            this.form.get('estadoCrm')?.setValidators([Validators.required]);

            this.form.get('dataFormacao')?.setValidators([Validators.required]);

            this.form.get('especialidade')?.setValidators([
                Validators.required,
                Validators.minLength(3)
            ]);

            this.form.get('tipoPix')?.setValidators([Validators.required]);

            this.form.get('chavePix')?.setValidators([
                Validators.required,
                Validators.minLength(5)
            ]);
        }

        if (this.tipoUsuario === 'instituicao') {

            this.form.get('enderecoCompleto')?.setValidators([Validators.required]);
            this.form.get('fotoPerfil')?.setValidators([Validators.required]);

        }

        this.form.updateValueAndValidity();

    }


    /* =====================================================
    FINALIZAÇÃO DO CADASTRO
    ===================================================== */

    finalizarCadastro() {

        this.erroCadastro = '';

        if (this.form.invalid) {
            this.form.markAllAsTouched();
            this.erroCadastro = 'Preencha todos os campos obrigatórios.';
            return;
        }

        const dadosEtapa3: DadosEtapa3 = {};

        if (this.tipoUsuario === 'profissional') {

            dadosEtapa3.crmNumero = this.form.value.crm!;
            dadosEtapa3.crms = this.form.value.estadoCrm!;
            dadosEtapa3.dataFormacao = this.form.value.dataFormacao!;
            dadosEtapa3.especialidades = this.form.value.especialidade!;
            dadosEtapa3.tipoPix = this.form.value.tipoPix!;
            dadosEtapa3.chavePix = this.form.value.chavePix!;

        }

        if (this.tipoUsuario === 'instituicao') {

            dadosEtapa3.enderecoCompleto = this.form.value.enderecoCompleto!;
            dadosEtapa3.fotoPerfil = this.fotoBase64;

        }

        this.cadastroService.setDadosEtapa3(dadosEtapa3);

        this.cadastroService.finalizarCadastro().subscribe({

            next: () => {
                this.modalSucesso.set(true);

                setTimeout(() => {
                    this.router.navigate(['/login']);
                }, 2000);
            },

            error: () => {
                this.modalErro.set(true);
            }

        });

    }


    /* =====================================================
    MODAIS
    ===================================================== */

    fecharModalSucesso() {
        this.modalSucesso.set(false);
        this.router.navigate(['/login']);
    }

    fecharModalErro() {
        this.modalErro.set(false);
    }


    /* =====================================================
    UPLOAD DE FOTO
    ===================================================== */

    onFotoSelecionada(event: any) {
        const file = event.files?.[0];
        if (!file) return;

        this.definirArquivo(file);
    }

    onDrop(event: DragEvent) {
        event.preventDefault();
        const file = event.dataTransfer?.files?.[0];
        if (!file) return;

        this.definirArquivo(file);
    }

    onDragOver(event: DragEvent) {
        event.preventDefault();
    }

    cancelarArquivo(event?: Event) {

        event?.stopPropagation();

        this.arquivoSelecionado = null;
        this.previewImagem = null;

        this.form.get('fotoPerfil')?.reset();

    }

    private definirArquivo(file: File) {

        this.arquivoSelecionado = file;

        const reader = new FileReader();

        reader.onload = () => {
            this.previewImagem = reader.result as string;
        };

        reader.readAsDataURL(file);

        this.form.get('fotoPerfil')?.setValue(file);

    }


    /* =====================================================
    DADOS DAS ETAPAS ANTERIORES
    ===================================================== */

    private carregarDadosEtapa2() {

        const dados = this.cadastroService.getDadosEtapa2();

        if (!dados) return;

        this.nome.set(dados.nome);
        this.email.set(dados.email);
        this.telefone.set(dados.telefone);

    }


    /* =====================================================
    ÍCONES
    ===================================================== */

    svgFechar = `
        <svg width="26" height="26" viewBox="0 0 26 26" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M14.4888 13.7466L9.48875 18.7466C9.3489 18.8866 9.17064 18.9819 8.97656 19.0206C8.78247 19.0592 8.58128 19.0394 8.39846 18.9637C8.21564 18.8879 8.0594 18.7596 7.94953 18.595C7.83966 18.4305 7.78109 18.237 7.78125 18.0391V14.0391H-0.21875C-0.483966 14.0391 -0.73832 13.9337 -0.925857 13.7462C-1.11339 13.5586 -1.21875 13.3043 -1.21875 13.0391C-1.21875 12.7738 -1.11339 12.5195 -0.925857 12.332C-0.73832 12.1444 -0.483966 12.0391 -0.21875 12.0391H7.78125V8.03906C7.78109 7.84117 7.83966 7.64767 7.94953 7.48308C8.0594 7.31849 8.21564 7.19019 8.39846 7.11444C8.58128 7.03869 8.78247 7.01888 8.97656 7.05753C9.17064 7.09618 9.3489 7.19155 9.48875 7.33156L14.4888 12.3316C14.5817 12.4244 14.6555 12.5347 14.7058 12.6561C14.7561 12.7775 14.782 12.9076 14.782 13.0391C14.782 13.1705 14.7561 13.3006 14.7058 13.422C14.6555 13.5434 14.5817 13.6537 14.4888 13.7466ZM21.7812 1.03906H13.7812C13.516 1.03906 13.2617 1.14442 13.0741 1.33196C12.8866 1.51949 12.7812 1.77385 12.7812 2.03906C12.7812 2.30428 12.8866 2.55863 13.0741 2.74617C13.2617 2.93371 13.516 3.03906 13.7812 3.03906H20.7812V23.0391H13.7812C13.516 23.0391 13.2617 23.1444 13.0741 23.332C12.8866 23.5195 12.7812 23.7738 12.7812 24.0391C12.7812 24.3043 12.8866 24.5586 13.0741 24.7462C13.2617 24.9337 13.516 25.0391 13.7812 25.0391H21.7812C22.0465 25.0391 22.3008 24.9337 22.4884 24.7462C22.6759 24.5586 22.7812 24.3043 22.7812 24.0391V2.03906C22.7812 1.77385 22.6759 1.51949 22.4884 1.33196C22.3008 1.14442 22.0465 1.03906 21.7812 1.03906Z" fill="white"/>
        </svg>
    `;
    svgEditar = `
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M22.8109 7.62821L16.3721 1.18946C16.2328 1.05014 16.0675 0.939617 15.8855 0.864214C15.7034 0.78881 15.5084 0.75 15.3114 0.75C15.1143 0.75 14.9193 0.78881 14.7372 0.864214C14.5552 0.939617 14.3899 1.05014 14.2506 1.18946L11.5843 3.85571L6.14682 5.89571C5.89974 5.98765 5.68126 6.14308 5.51338 6.34634C5.3455 6.54961 5.23416 6.79352 5.19057 7.05352L3.24338 18.7348C3.23033 18.8131 3.24248 18.8935 3.27807 18.9645C3.31367 19.0355 3.37087 19.0934 3.44145 19.1297C3.51203 19.1661 3.59234 19.1792 3.6708 19.167C3.74927 19.1548 3.82184 19.118 3.87807 19.062L9.22182 13.7182C9.05251 13.366 8.97849 12.9755 9.00713 12.5857C9.04199 12.1062 9.22953 11.6505 9.54227 11.2853C9.855 10.9201 10.2765 10.6647 10.7449 10.5565C11.2134 10.4484 11.7042 10.4931 12.1454 10.6841C12.5865 10.8752 12.9549 11.2026 13.1965 11.6182C13.4381 12.0339 13.5402 12.516 13.4878 12.994C13.4354 13.4719 13.2314 13.9205 12.9055 14.2739C12.5796 14.6274 12.149 14.8672 11.677 14.9582C11.2049 15.0492 10.716 14.9865 10.2821 14.7795L4.93838 20.1232C4.88231 20.1794 4.84554 20.252 4.83336 20.3305C4.82118 20.4089 4.83422 20.4893 4.87061 20.5598C4.90699 20.6304 4.96484 20.6876 5.03582 20.7232C5.1068 20.7588 5.18725 20.771 5.26557 20.7579L16.9478 18.8107C17.2073 18.7675 17.451 18.6567 17.6542 18.4895C17.8574 18.3223 18.0131 18.1046 18.1056 17.8582L20.1465 12.4141L22.8109 9.75071C22.9502 9.61142 23.0607 9.44605 23.1361 9.26403C23.2115 9.08202 23.2503 8.88694 23.2503 8.68993C23.2503 8.49292 23.2115 8.29784 23.1361 8.11583C23.0607 7.93381 22.9502 7.7675 22.8109 7.62821ZM19.5006 10.9395L13.0618 4.50071L15.3118 2.25071L21.7506 8.68946L19.5006 10.9395Z" fill="#020035"/>
        </svg>
    `;
}