import { Component, inject } from "@angular/core";
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";

import { AuthApiService } from "../../services/auth-api";
import { AuthService } from "../../services/auth";

import { LoginRequest } from "../../models/login-request";
import { LoginResponse } from "../../models/login-response";

@Component({
    selector: 'app-login-form',
    standalone: true,
    imports: [ReactiveFormsModule, CommonModule, RouterModule],
    templateUrl: './login-form.html'
})
export class LoginFormComponent {

    /* ---------------- DEPENDÊNCIAS ---------------- */

    private authApi = inject(AuthApiService);
    private authService = inject(AuthService);
    private router = inject(Router);


    /* ---------------- ESTADO ---------------- */

    erroLogin = "";

    mostrarSenha = false;


    /* ---------------- FORM ---------------- */

    loginForm = new FormGroup({

        email: new FormControl('', [
            Validators.required,
            Validators.email
        ]),

        senha: new FormControl('', [
            Validators.required
        ])

    });


    /* ---------------- GETTERS ---------------- */

    get email() {
        return this.loginForm.get('email');
    }

    get senha() {
        return this.loginForm.get('senha');
    }


    /* ---------------- UI ---------------- */

    toggleSenha() {
        this.mostrarSenha = !this.mostrarSenha;
    }


    /* ---------------- SUBMIT ---------------- */

    logar() {

        this.erroLogin = "";

        if (this.loginForm.invalid) {
            this.loginForm.markAllAsTouched();
            this.erroLogin = "Preencha todos os campos.";
            return;
        }

        const request: LoginRequest = {

            email: this.email?.value!,
            senha: this.senha?.value!

        };

        this.authApi.login(request).subscribe({

            next: (res: LoginResponse) => {

                this.authService.login(res);

                this.router.navigate(['/home']);

            },

            error: err => {

                if (err.status === 401) {
                    this.erroLogin = "Email ou senha incorretos.";
                    return;
                }

                if (err.status === 404) {
                    this.erroLogin = "Usuário não encontrado.";
                    return;
                }

                if (err.status === 0) {
                    this.erroLogin = "Não foi possível conectar ao servidor.";
                    return;
                }

                this.erroLogin = "Erro inesperado.";

            }

        });

    }

}