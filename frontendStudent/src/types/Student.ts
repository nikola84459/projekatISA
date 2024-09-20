import {PlacanjeResponse} from "./Rata";

export interface StudentPodaci {
    studentId: number,
    ime: string,
    prezime: string,
    brIndeksa: string,
    jmbg: string,
    email: string,
    stanjeNaRacunu: number
}

export interface UpdateStanjeNaRacunu {
    iznos: number,
    brojKartice: string,
    datumIsteka: string,
    cvv: string
}

export interface PromenaSifreRequest {
    staraSifra: string,
    sifra: string,
    ponovoNovaSifra: string,
}

export interface IResult {
    success: boolean,
    message?: string
}

export interface Token {
    authToekn: string,
    refreshToken: string
}

export interface IResultsLogin {
    success: boolean,
    message?: string
    podaci?: Token
}

export interface RequestLogin {
    username: string,
    password: string
}

export interface IProps {
    searchParams?: { [key: string]: string | string[] | undefined };
}