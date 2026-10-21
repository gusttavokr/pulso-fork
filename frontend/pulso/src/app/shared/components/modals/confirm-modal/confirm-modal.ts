import { CommonModule } from "@angular/common";
import { Component, EventEmitter, Input, Output } from "@angular/core";
import { ModalComponent } from "../../modal/modal";
import { ButtonComponent } from "../../button/button";

@Component({
    selector: 'app-confirm-modal',
    imports: [
        CommonModule,
        ModalComponent,
        ButtonComponent
    ],
    templateUrl: 'confirm-modal.html'
})
export class ConfirmModalComponent {

    @Input() visible = false;

    @Input() title = 'Confirmar ação';
    @Input() message = '';
    @Input() subMessage = 'Esta ação não pode ser desfeita.';

    @Input() confirmLabel = 'Confirmar';
    @Input() cancelLabel = 'Cancelar';

    @Output() confirmed = new EventEmitter<void>();
    @Output() cancelled = new EventEmitter<void>();
    @Output() closed = new EventEmitter<void>();

    confirmar() {
        this.confirmed.emit();
    }

    cancelar() {
        this.cancelled.emit();
        this.closed.emit();
    }

}