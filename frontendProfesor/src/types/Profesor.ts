import exp from "constants";

export interface ProfesorPodaci {
    ime: string,
    prezime: string,
    kime: string,
    email: string
}

export interface RequestSifra {
    staraSifra: string,
    sifra: string,
    ponovoNovaSifra: string
}

export interface IResults {
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