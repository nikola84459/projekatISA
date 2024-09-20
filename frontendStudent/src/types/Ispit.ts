import {AktivnostPodaci, PredmetPodaci, StudentPredmet} from "./Predmet";
import {IspitniRokPodaci} from "./IspitniRok";

export interface PrijavljeniPodaci {
    predmet: PredmetPodaci
    ispitniRok: IspitniRokPodaci,
    ispit: IspitPodaci,
    zakljucen: boolean,
    polozen: boolean
}

export interface IspitPodaci {
    ispitId: number,
    ocena: number,
    bodovi: number,
    bodoviUkupno: number,
    izasao: boolean
}

export interface ZakljuceniPodaci {
    ispitPrijavaId: number
    studentPredmet: StudentPredmet,
    ispitniRok: IspitniRokPodaci,
    ispit: IspitPodaci
}

export interface IspitDetaljiPodaci {
    ispitniRokId: number
    ispitniRokNaziv: string,
    predmetNaziv: string,
    vremeIspita: string,
    cenaPrijave: number,
    redovnaPrijava: boolean

}

export interface IspitPrijavaRequest {
    predmetId: number,
    ispitniRokId: number;
}

export interface IResults {
    success: boolean,
    message?: string,
    podaci?: PrijavljeniPodaci[] | IspitniRokPodaci[] | IspitDetaljiPodaci
}


export function isIspitDetaljiPodaci(data: any): data is IspitDetaljiPodaci {
    return (data as IspitDetaljiPodaci).ispitniRokId !== undefined &&
           (data as IspitDetaljiPodaci).ispitniRokNaziv !== undefined &&
           (data as IspitDetaljiPodaci).predmetNaziv !== undefined &&
           (data as IspitDetaljiPodaci).vremeIspita !== undefined &&
           (data as IspitDetaljiPodaci).cenaPrijave !== undefined &&
           (data as IspitDetaljiPodaci).redovnaPrijava !== undefined
}