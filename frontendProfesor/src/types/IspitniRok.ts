export interface IspitniRok {
    ispitniRokId: number,
    naziv: string,
    pocetakPrijave: Date,
    pocetak: Date,
    kraj: Date
}

export interface IResultsIspitniRok {
    success: boolean,
    message?: string;
    podaci?: IspitniRok[]
}