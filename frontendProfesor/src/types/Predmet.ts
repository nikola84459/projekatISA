import {StudentPodaci} from "./Student";
import {IspitniRok} from "./IspitniRok";

export interface PredmetPodaci {
    predmetId: number,
    naziv: string,
    akronim: string,
    espb: string
}

export interface AktivnostPodaci {
    studentPredmetId: number
    aktivnost: number,
    prviKolokvijum: number
    drugiKolokvijum: number
    student?: StudentPodaci
}

export interface AktivnostRequest {
    aktivnostPodaci: AktivnostPodaci[];
}

export interface IResults {
    success: boolean,
    message?: string;
    podaci?: AktivnostPodaci[]
}


export interface RequestPredmetId {
    predmetId: number
}