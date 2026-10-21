import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

import { NavItem } from './models/navigation';
import { buildNavigation } from './config/navigation';
import { AuthService } from '../../../../../../features/autenticacao/services/auth';

@Component({
  selector: 'app-navigation',
  imports: [CommonModule, RouterModule],
  templateUrl: './navigation.html'
})
export class NavigationComponent {

  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  navItems: NavItem[] = buildNavigation(this.authService);

  changeActive(clickedItem: NavItem): void {

    this.navItems.forEach(item => {
      if (item.label !== 'Sair') {
        item.isActive = (item === clickedItem);
      }
    });

    if (clickedItem.href) {
      this.router.navigate([clickedItem.href]);
    }
  }

  toggleDropdown(item: NavItem): void {
    item.isOpen = !item.isOpen;
  }

  onItemClick(item: NavItem): void {

    if (item.action) {
      item.action();
      this.router.navigate(['/login']);
      return;
    }

    this.changeActive(item);
  }

}