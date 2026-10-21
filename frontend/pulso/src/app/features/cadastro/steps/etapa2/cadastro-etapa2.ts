import { Component, inject, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router } from "@angular/router";
import {
    FormControl,
    FormGroup,
    Validators,
    ReactiveFormsModule,
    ValidatorFn,
    AbstractControl,
    ValidationErrors
} from "@angular/forms";
import { PasswordModule } from 'primeng/password';
import { DatePickerModule } from "primeng/datepicker";
import { InputTextModule } from "primeng/inputtext";
import { CadastroService } from "../../services/cadastro";
import { DadosEtapa2 } from "../../models/cadastro-etapa2";

@Component({
    selector: 'app-cadastro-etapa2',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        CommonModule,
        PasswordModule,
        DatePickerModule,
        InputTextModule,
    ],
    templateUrl: 'cadastro-etapa2.html'
})
export class CadastroEtapa2Component implements OnInit {

    readonly _cadastroService = inject(CadastroService);
    readonly _router = inject(Router);

    erroCadastro = "";
    mostrarSenha = false;
    mostrarConfirmarSenha = false;

    tipoUsuario: 'profissional' | 'instituicao' | null = null;

    form = new FormGroup({
        nome: new FormControl('', [
            Validators.required,
            Validators.minLength(3)
        ]),

        email: new FormControl('', [
            Validators.required,
            Validators.email
        ]),

        dataNascimento: new FormControl(''),

        telefone: new FormControl('', [
            Validators.required
        ]),

        cnpj: new FormControl(''),

        senha: new FormControl('', [
            Validators.required,
            Validators.minLength(6)
        ]),

        confirmarSenha: new FormControl('', [
            Validators.required
        ]),
    });

    get f() {
        return this.form.controls;
    }

    ngOnInit() {
        this.tipoUsuario = this._cadastroService.getTipoUsuario();

        if (!this.tipoUsuario) {
            this._router.navigate(['/cadastro/etapa1']);
        }

        this.form.get('telefone')?.setValidators([
            Validators.required,
            this.validarFormatoTelefone()
        ]);

        if (this.tipoUsuario === 'instituicao') {
            this.form.get('cnpj')?.setValidators([
                Validators.required,
                this.validarFormatoCNPJ(),
                this.validarCNPJ()
            ]);
        }

        this.form.get('telefone')?.valueChanges.subscribe(value => {
            if (value) {
                const formatted = this.formatarTelefone(value);
                if (formatted !== value) {
                    this.form.get('telefone')?.setValue(formatted, { emitEvent: false });
                }
            }
        });

        this.form.get('cnpj')?.valueChanges.subscribe(value => {
            if (value) {
                const formatted = this.formatarCNPJ(value);
                if (formatted !== value) {
                    this.form.get('cnpj')?.setValue(formatted, { emitEvent: false });
                }
            }
        });
    }

    formatarTelefone(telefone: string): string {
        const numeros = telefone.replace(/\D/g, '');

        if (!numeros) return '';

        const numerosLimitados = numeros.substring(0, 11);

        if (numerosLimitados.length <= 2) {
            return `(${numerosLimitados}`;
        } else if (numerosLimitados.length <= 7) {
            return `(${numerosLimitados.substring(0, 2)}) ${numerosLimitados.substring(2)}`;
        } else {
            return `(${numerosLimitados.substring(0, 2)}) ${numerosLimitados.substring(2, 7)}-${numerosLimitados.substring(7, 11)}`;
        }
    }

    validarFormatoTelefone(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            if (!control.value) return null;

            const telefone = control.value.replace(/\D/g, '');

            if (telefone.length !== 11) {
                return { telefoneFormatoInvalido: true };
            }

            return null;
        };
    }

    formatarCNPJ(cnpj: string): string {
        const numeros = cnpj.replace(/\D/g, '');

        if (!numeros) return '';

        const numerosLimitados = numeros.substring(0, 14);

        if (numerosLimitados.length <= 2) {
            return numerosLimitados;
        } else if (numerosLimitados.length <= 5) {
            return `${numerosLimitados.substring(0, 2)}.${numerosLimitados.substring(2)}`;
        } else if (numerosLimitados.length <= 8) {
            return `${numerosLimitados.substring(0, 2)}.${numerosLimitados.substring(2, 5)}.${numerosLimitados.substring(5)}`;
        } else if (numerosLimitados.length <= 12) {
            return `${numerosLimitados.substring(0, 2)}.${numerosLimitados.substring(2, 5)}.${numerosLimitados.substring(5, 8)}/${numerosLimitados.substring(8)}`;
        } else {
            return `${numerosLimitados.substring(0, 2)}.${numerosLimitados.substring(2, 5)}.${numerosLimitados.substring(5, 8)}/${numerosLimitados.substring(8, 12)}-${numerosLimitados.substring(12, 14)}`;
        }
    }

    validarFormatoCNPJ(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            if (!control.value) return null;

            const cnpj = control.value.replace(/\D/g, '');

            if (cnpj.length !== 14) {
                return { cnpjFormatoInvalido: true };
            }

            return null;
        };
    }

    validarCNPJ(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            if (!control.value) return null;

            const cnpj = control.value.replace(/\D/g, '');

            if (cnpj.length !== 14) {
                return null;
            }

            if (/^(\d)\1+$/.test(cnpj)) {
                return { cnpjInvalido: true };
            }

            let tamanho = cnpj.length - 2;
            let numeros = cnpj.substring(0, tamanho);
            let digitos = cnpj.substring(tamanho);
            let soma = 0;
            let pos = tamanho - 7;

            for (let i = tamanho; i >= 1; i--) {
                soma += parseInt(numeros.charAt(tamanho - i)) * pos--;
                if (pos < 2) pos = 9;
            }

            let resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
            if (resultado !== parseInt(digitos.charAt(0))) {
                return { cnpjInvalido: true };
            }

            tamanho = tamanho + 1;
            numeros = cnpj.substring(0, tamanho);
            soma = 0;
            pos = tamanho - 7;

            for (let i = tamanho; i >= 1; i--) {
                soma += parseInt(numeros.charAt(tamanho - i)) * pos--;
                if (pos < 2) pos = 9;
            }

            resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
            if (resultado !== parseInt(digitos.charAt(1))) {
                return { cnpjInvalido: true };
            }

            return null;
        };
    }

    toggleSenha() {
        this.mostrarSenha = !this.mostrarSenha;
    }

    toggleConfirmarSenha() {
        this.mostrarConfirmarSenha = !this.mostrarConfirmarSenha;
    }

    proximo() {
        this.erroCadastro = "";

        if (this.form.invalid) {
            Object.keys(this.form.controls).forEach(key => {
                const control = this.form.get(key);
            });
            this.form.markAllAsTouched();
            return;
        }

        if (this.form.value.senha !== this.form.value.confirmarSenha) {
            this.erroCadastro = "As senhas não coincidem.";
            return;
        }

        const telefoneControl = this.form.get('telefone');
        if (telefoneControl?.invalid) {
            if (telefoneControl.errors?.['required']) {
                this.erroCadastro = "Telefone é obrigatório.";
            } else if (telefoneControl.errors?.['telefoneFormatoInvalido']) {
                this.erroCadastro = "Telefone deve conter 11 números.";
            }
            return;
        }

        if (this.tipoUsuario === 'instituicao') {
            const cnpjControl = this.form.get('cnpj');
            if (cnpjControl?.invalid) {
                if (cnpjControl.errors?.['required']) {
                    this.erroCadastro = "CNPJ é obrigatório para instituições.";
                } else if (cnpjControl.errors?.['cnpjFormatoInvalido']) {
                    this.erroCadastro = "CNPJ deve conter 14 números.";
                } else if (cnpjControl.errors?.['cnpjInvalido']) {
                    this.erroCadastro = "CNPJ inválido.";
                }
                return;
            }
        }

        const dadosEtapa2: DadosEtapa2 = {
            nome: this.form.value.nome!,
            email: this.form.value.email!,
            telefone: this.form.value.telefone!,
            senha: this.form.value.senha!,
            confirmarSenha: this.form.value.confirmarSenha!,
        };

        if (this.tipoUsuario === 'profissional') {
            dadosEtapa2.dataNascimento = this.form.value.dataNascimento!;
        }

        if (this.tipoUsuario === 'instituicao') {
            dadosEtapa2.cnpj = this.form.value.cnpj!;
        }

        this._cadastroService.setDadosEtapa2(dadosEtapa2);
        this._cadastroService.avancarPasso();

        this._router.navigate(['/cadastro/etapa3']);

    }

    obterMensagemErroTelefone(): string {
        const control = this.form.get('telefone');

        if (!control || !control.errors) return '';

        if (control.errors['required']) {
            return 'Telefone é obrigatório';
        }

        if (control.errors['telefoneFormatoInvalido']) {
            return 'Telefone deve conter 11 números (ex: 11999999999)';
        }

        return '';
    }

    obterMensagemErroCNPJ(): string {
        const control = this.form.get('cnpj');

        if (!control || !control.errors) return '';

        if (control.errors['required']) {
            return 'CNPJ é obrigatório';
        }

        if (control.errors['cnpjFormatoInvalido']) {
            return 'CNPJ deve conter 14 números';
        }

        if (control.errors['cnpjInvalido']) {
            return 'CNPJ inválido';
        }

        return '';
    }
}