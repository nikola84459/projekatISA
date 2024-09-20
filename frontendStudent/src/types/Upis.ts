export interface UpisPodaci {
    upisId: number,
    smer: SmerPodaci,
    akademskaGodina: string,
    godinaStudija: string,
    najavaEspb: number,
    ostvarenoEspb: number,
    aktivan: boolean
}

export interface SmerPodaci {
    smerId: number,
    naziv: string
    akronim: string,
    cenaŠkolarine: string
}