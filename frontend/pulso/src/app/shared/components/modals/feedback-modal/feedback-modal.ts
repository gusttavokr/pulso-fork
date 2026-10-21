import { Component, EventEmitter, Input, Output } from "@angular/core";
import { ButtonComponent } from "../../button/button";
import { CommonModule } from "@angular/common";
import { ModalComponent } from "../../modal/modal";

@Component({
    selector: 'app-feedback-modal',
    imports: [
        CommonModule,
        ModalComponent,
        ButtonComponent
    ],
    templateUrl: 'feedback-modal.html',
    styleUrls: ['feedback-modal.scss']
})
export class FeedbackModalComponent {

    @Input() visible = false;

    @Input() modalTitle = '';
    @Input() message = '';
    @Input() subMessage = '';

    @Input() type: 'success' | 'error' = 'success';

    @Input() animateIcon = true;

    @Input() primarySvg?: string;
    @Input() secondarySvg?: string;

    @Input() primaryLabel = 'OK';
    @Input() primaryVariant: 'confirmacao' | 'excluir' | 'borda' = 'confirmacao';
    @Output() primaryClick = new EventEmitter<void>();

    @Input() secondaryLabel?: string;
    @Input() secondaryVariant: 'borda' | 'excluir' | 'confirmacao' = 'borda';
    @Output() secondaryClick = new EventEmitter<void>();

    @Input() buttonSvg?: string;

    @Output() closed = new EventEmitter<void>();

    fechar() {
        this.closed.emit();
    }

    onPrimary() {
        this.primaryClick.emit();
    }

    onSecondary() {
        this.secondaryClick.emit();
    }

}