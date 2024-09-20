import ProfesorPodaci from "./Profesor";

export interface PredmetPodaci {
    predmetId: number;
    naziv: string;
    akronim: string;
    espb: number;
    profesor: ProfesorPodaci
}

export interface PredmetRequest {
    predmetId: number
}

export interface AktivnostPodaci {
    aktivnost: number,
    prviKolokvijum: number,
    drugiKolokvijum: number
}

export interface StudentPredmet {
    student_predmet_id: number,
    aktivnost: number,
    prviKolokvijum: number,
    drugiKolokvijum: number,
    predmet: PredmetPodaci
}
export interface IResults {
    success: boolean,
    message?: string,
    podaci?: AktivnostPodaci
}