export interface PlantaoFiltro {
    abertos?: boolean;

    data?: string; // yyyy-MM-dd
    especialidade?: string;

    precoMin?: number | null;
    precoMax?: number | null;

    horariosInicio?: string[]; // HH:mm
    horariosFim?: string[];    // HH:mm

    ehNovoPlantao?: boolean;
    ehRepasse?: boolean;
    ehTroca?: boolean;

    profissional?: boolean;
    liderEscala?: boolean;
    instituicao?: boolean;
}