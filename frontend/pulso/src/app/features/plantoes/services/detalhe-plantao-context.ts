import { Injectable, signal } from "@angular/core";
import { DetalhePlantaoContext } from "../../../shared/enums/detalhe-plantao-context";

@Injectable({ providedIn: 'root' })
export class DetalhePlantaoContextService {

    private _context = signal<DetalhePlantaoContext | null>(null);

    set(context: DetalhePlantaoContext) {
        this._context.set(context);
    }

    get context() {
        return this._context();
    }

    clear() {
        this._context.set(null);
    }
}