import { Component, inject, OnInit, signal } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './layout/components/header/header';
import { CommonModule } from '@angular/common';
import { SidebarCalendarComponent } from './layout/components/sidebars/sidebar-calendario/sidebar-calendar';
import { SidebarService } from './layout/services/sidebar';
import { SidebarComponent } from './layout/components/sidebars/sidebar/sidebar';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    SidebarComponent,
    SidebarCalendarComponent,
    HeaderComponent,
    CommonModule
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  readonly sidebarService = inject(SidebarService);
  private readonly router = inject(Router);

  protected readonly title = signal('pulso');
  protected mostrarLayout = signal(true);

  ngOnInit(): void {

    // escuta mudanças de rota
    this.router.events.subscribe(event => {

      if (event instanceof NavigationEnd) {

        const url = event.urlAfterRedirects;

        this.mostrarLayout.set(
          !(url.includes('login') || url.includes('cadastro'))
        );

      }

    });

  }
}
