import { CommonModule } from '@angular/common';
import {
  booleanAttribute,
  Component,
  computed,
  inject,
  Input
} from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

export type ButtonVariant = 'confirmacao' | 'borda' | 'excluir';

export interface BorderColors {
  main: string;
  hover?: string;
  text: string;
  background?: string;
}

@Component({
  selector: 'app-button',
  imports: [CommonModule, ButtonModule],
  templateUrl: './button.html',
  styleUrls: ['./button.scss']
})
export class ButtonComponent {

  @Input({ required: true }) label!: string;
  @Input({ required: true }) variant!: ButtonVariant;

  @Input({ transform: booleanAttribute }) disabled = false;
  @Input({ transform: booleanAttribute }) hasIcon = true;

  @Input() iconSize = 20;
  @Input() borderColors?: BorderColors;
  @Input() buttonClass = '';

  safeSvg?: SafeHtml;

  private sanitizer = inject(DomSanitizer);
  private _svgIcon?: string;

  @Input()
  set svgIcon(value: string | undefined) {
    this._svgIcon = value;
    this.safeSvg = value
      ? this.sanitizer.bypassSecurityTrustHtml(value)
      : undefined;
  }

  get svgIcon() {
    return this._svgIcon;
  }

  readonly showIcon = computed(() => this.hasIcon && !!this.svgIcon);

  readonly styles = computed(() => {
    if (this.variant !== 'borda') return {};

    const colors = this.borderColors ?? {
      main: '#020035',
      text: '#020035',
      background: '#ffffff'
    };

    return {
      '--btn-border': colors.main,
      '--btn-color': colors.text,
      '--btn-bg': colors.background ?? '#ffffff'
    };
  });
}