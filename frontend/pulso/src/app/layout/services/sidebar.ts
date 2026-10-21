import { Injectable, signal } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class SidebarService {

    sidebarAberta = signal(true);

    setSidebarAberta(valor: boolean) {
        this.sidebarAberta.set(valor);
    }

}