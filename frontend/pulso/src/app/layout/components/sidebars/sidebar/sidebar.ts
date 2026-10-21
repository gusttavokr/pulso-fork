import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../../features/autenticacao/services/auth';
import { TipoUsuario } from '../../../../shared/enums/tipo-usuario';
import { NavigationComponent } from './components/navigation/navigation';

@Component({
  selector: 'app-sidebar',
  imports: [
    CommonModule,
    NavigationComponent
  ],
  templateUrl: 'sidebar.html',
})
export class SidebarComponent {

  readonly auth = inject(AuthService);

  sidebarVisivel = true;

  cargaHoraria = 60;

  TipoUsuario = TipoUsuario;
}
