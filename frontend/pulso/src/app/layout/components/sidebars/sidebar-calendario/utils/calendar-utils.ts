import { SidebarCalendarioModel } from "../models/sidebar-calendar";
import { CalendarGroup } from "../models/calendar-group";

export function agruparEventosPorData(lista: SidebarCalendarioModel[]): CalendarGroup[] {

    const grupos: Record<string, CalendarGroup> = {};

    lista.forEach(item => {

        if (!item.dia || isNaN(item.dia.getTime())) {
            return;
        }

        const key = item.dia.toDateString();

        if (!grupos[key]) {
            grupos[key] = { data: item.dia, itens: [] };
        }

        grupos[key].itens.push(item);

    });

    Object.values(grupos).forEach(grupo => {
        grupo.itens.sort((a, b) => {

            if (a.horario.hours !== b.horario.hours) {
                return a.horario.hours - b.horario.hours;
            }

            return a.horario.minutes - b.horario.minutes;

        });
    });

    return Object.values(grupos).sort((a, b) => {

        const hoje = new Date();
        const amanha = new Date();
        amanha.setDate(hoje.getDate() + 1);

        if (a.data.toDateString() === hoje.toDateString()) return -1;
        if (b.data.toDateString() === hoje.toDateString()) return 1;

        if (a.data.toDateString() === amanha.toDateString()) return -1;
        if (b.data.toDateString() === amanha.toDateString()) return 1;

        return a.data.getTime() - b.data.getTime();

    });

}


export function cabecalhoData(data: Date): string {

    if (!data || isNaN(data.getTime())) {
        return 'Data inválida';
    }

    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);

    const amanha = new Date(hoje);
    amanha.setDate(amanha.getDate() + 1);

    const comparar = new Date(data);
    comparar.setHours(0, 0, 0, 0);

    if (comparar.getTime() === hoje.getTime()) return 'Hoje';
    if (comparar.getTime() === amanha.getTime()) return 'Amanhã';

    const meses = [
        'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];

    const dias = [
        'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'
    ];

    return `${dias[data.getDay()]}, ${data.getDate()} de ${meses[data.getMonth()]}`;

}


export function formatarHorario(hours: number, minutes: number): string {

    const h = isNaN(hours) ? 0 : hours;
    const m = isNaN(minutes) ? 0 : minutes;

    return `${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}`;

}