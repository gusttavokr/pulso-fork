import { Component } from '@angular/core';
import { LoginFormComponent } from '../../components/login-form/login-form';

@Component({
  selector: 'app-login-page',
  imports: [LoginFormComponent],
  templateUrl: './login.html'
})
export class LoginPage {}