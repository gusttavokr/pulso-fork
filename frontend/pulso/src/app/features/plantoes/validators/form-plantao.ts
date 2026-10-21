import { ValidatorFn } from '@angular/forms';

export const futureOrTodayDateValidator: ValidatorFn = (control) => {
    if (!control.value) return null;

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const value = new Date(control.value);
    value.setHours(0, 0, 0, 0);

    return value < today ? { dateInPast: true } : null;
};

export const validTimeValidator: ValidatorFn = (control) => {
    if (!control.value) return null;

    const h = control.value.getHours();
    const m = control.value.getMinutes();

    return (h < 0 || h > 23 || m < 0 || m > 59)
        ? { invalidTime: true }
        : null;
};

export const horaFinalDepoisInicialValidator: ValidatorFn = (group) => {
    const i = group.get('dataInicio')?.value;
    const hi = group.get('horaInicio')?.value;
    const f = group.get('dataEncerramento')?.value;
    const hf = group.get('horaEncerramento')?.value;

    if (!i || !hi || !f || !hf) return null;

    const inicio = new Date(i);
    inicio.setHours(hi.getHours(), hi.getMinutes());

    const fim = new Date(f);
    fim.setHours(hf.getHours(), hf.getMinutes());

    return fim <= inicio ? { horaEncerramentoInvalida: true } : null;
};

export const dataEncerramentoDepoisInicioValidator: ValidatorFn = (group) => {
    const i = group.get('dataInicio')?.value;
    const f = group.get('dataEncerramento')?.value;

    if (!i || !f) return null;

    return new Date(f) < new Date(i)
        ? { dataEncerramentoAntesInicio: true }
        : null;
};