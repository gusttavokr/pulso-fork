import { Injectable, signal } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class CriacaoPlantaoContextService {

    private _escalaId = signal<number | null>(null);

    setEscalaId(id: number) {
        this._escalaId.set(id);
    }

    get escalaId() {
        return this._escalaId();
    }

    clear() {
        this._escalaId.set(null);
    }
    
}