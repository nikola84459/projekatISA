export interface IspitniRokPodaci {
    ispitniRokId: number,
    naziv: string,
    pocetak: string,
    kraj: string,
    pocetakPrijave: string,
    cenaRedovnePrijave: number,
    cenaVanrednePrijave: number
}

export function isIspitniRokPodaci(data: any): data is IspitniRokPodaci {
    return (data as IspitniRokPodaci).ispitniRokId !== undefined &&
           (data as IspitniRokPodaci).naziv !== undefined &&
           (data as IspitniRokPodaci).pocetak !== undefined &&
           (data as IspitniRokPodaci).kraj !== undefined &&
           (data as IspitniRokPodaci).pocetakPrijave !== undefined &&
           (data as IspitniRokPodaci).cenaRedovnePrijave !== undefined &&
           (data as IspitniRokPodaci).cenaVanrednePrijave !== undefined
}