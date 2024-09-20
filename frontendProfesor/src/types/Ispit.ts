import {AktivnostPodaci} from "./Predmet";
import {IspitniRok} from "./IspitniRok";

export interface IspitPodaci {
    ispitPrijavaId: number
    studentPredmet: AktivnostPodaci,
    ispitniRok: IspitniRok
    ispit: Ispit
}

export interface Ispit {
    ocena: number,
    bodovi: number | null,
    bodoviUkupno: number | null,
    izasao: number

}

export interface RequestZakljuci {
    ispIds: number[];
}
export interface RequestGetPrijava {
    predmetId: number,
    ispitniRokId: number
}

export interface AddIspit {
    aktivnost: number
    prviKolokvijum: number,
    drugiKolokvijum: number,
    ispitBodovi: number,
    ispitOcena: number
}

export interface RequestAddIspit {
    ispitPodaci: AddIspit[]
}

export interface IResults {
    success: boolean,
    message?: string,
    podaci?: IspitPodaci[]
}