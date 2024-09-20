import {IspitniRokPodaci} from "./IspitniRok";
import {IspitDetaljiPodaci, PrijavljeniPodaci} from "./Ispit";

export interface Rata {
    rataId: number,
    skolskaGodina: string,
    iznos: number,
    rokPlacanja: string,
    placeno: boolean
}

export interface RataPodaci {
    rata: Rata[],
    ukupnoDugovanje: number,
    dugovanjeNaDanasnjiDan: number,
    ukupnoUplaceno: number
}

export interface RataPlacanjeRequest {
    rataIds: number[],
    brojKartice: string,
    datumIsteka: string,
    cvv: string
}

export interface IResults {
    success: boolean,
    podaci?: PlacanjeResponse
}

export interface PlacanjeResponse{
    brojTransakcije: string,
    uspesno: boolean,
    poruka: string
}