import { CommonModule } from "@angular/common";
import { Component, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild } from "@angular/core";

@Component({
    selector: 'app-modal',
    imports: [CommonModule],
    templateUrl: 'modal.html',
    styleUrls: ['modal.scss'],
})
export class ModalComponent {

    @Input() visible = false;
    @Input() closeOnBackdrop = true;

    @Input() showCloseButton = true;
    @Input() width: 'sm' | 'md' | 'lg' | 'xl' | 'full' = 'md';
    @Input() noPadding = false;
    @Input() customClass = '';

    @Output() closed = new EventEmitter<void>();

    @ViewChild('modalBox') modalBox!: ElementRef<HTMLElement>;

    fechar(): void {
        this.closed.emit();
    }

    onBackdropClick(event: MouseEvent): void {
        if (
            this.closeOnBackdrop &&
            !this.modalBox.nativeElement.contains(event.target as Node)
        ) {
            this.fechar();
        }
    }

    @HostListener('document:keydown.escape')
    onEscape(): void {
        if (this.visible) this.fechar();
    }

    getWidth(): string {
        switch (this.width) {
            case 'sm': return '520px';   // max-w-sm
            case 'md': return '768px';   // max-w-md
            case 'lg': return '1024px';   // max-w-lg
            case 'xl': return '825px';   // max-w-xl
            case 'full': return '100%';
            default: return '768px';
        }
    }
}