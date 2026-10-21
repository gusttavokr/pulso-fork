import { Especialidade } from "../../../../../shared/enums/especialidade";

export interface Time {
    hours: number;
    minutes: number;
}

export interface SidebarCalendarioModel {
    id: number;
    dia: Date;
    horario: Time;
    especialidade: Especialidade;
    link?: string;
    titulo?: string;
}