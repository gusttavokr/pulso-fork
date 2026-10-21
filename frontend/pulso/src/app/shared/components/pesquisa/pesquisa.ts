import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AutoComplete } from 'primeng/autocomplete';
import { CardModule } from 'primeng/card';
import { AutoCompleteEvent } from './models/auto-complete-event';

@Component({
    selector: 'app-pesquisa',
    imports: [AutoComplete, FormsModule, CardModule],
    templateUrl: './pesquisa.html',
})
export class PesquisaComponent {

    items: any[] = [];

    value: any;

    search(event: AutoCompleteEvent) {
        this.items = [...Array(10).keys()].map(item => event.query + '-' + item);
    }
    
} 