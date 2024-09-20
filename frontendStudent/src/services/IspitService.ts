import {api} from "../data/api";
import {IspitPrijavaRequest, PrijavljeniPodaci, ZakljuceniPodaci} from "../types/Ispit";
import {PredmetRequest} from "../types/Predmet";
import {IResults} from "../types/Ispit"
import {useSession} from "next-auth/react";

export default class IspitService {
    public static async getPrijavljeniForStudent(): Promise<PrijavljeniPodaci[] | null > {
        return new Promise<PrijavljeniPodaci[] | null>( async resolve => {
              await api("get", "ispit/getPrijavljenByStudentId")
                .then(res => {
                    if(res.status !== "ok") {
                        return resolve(null);
                    }

                    resolve(res.data as PrijavljeniPodaci[]);
                })
        })
    }

    public static async getPolozeni(): Promise<ZakljuceniPodaci[] | null> {
        return new Promise<ZakljuceniPodaci[] | null >(async resolve => {
            await api("get", "ispit/polozeniForStudent")
                .then(res => {
                    if(res.status !== "ok") {
                        return resolve(null);
                    }

                    resolve(res.data as ZakljuceniPodaci[]);
                })
        })
    }

    public static async getNePolozeni(): Promise<ZakljuceniPodaci[] | null> {
        return new Promise<ZakljuceniPodaci[] | null>(async resolve => {
            await api("get", "ispit/neuspesnaPolaganja")
                .then(res => {
                    if(res.status !== "ok") {
                        return resolve(null);
                    }

                    resolve(res.data as ZakljuceniPodaci[]);
                })
        })
    }

    public static sendPredmetId(data: PredmetRequest): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "ispit/getIspitniRok", data)
                .then(res => {
                    if(res.status === "error") {
                        if(Array.isArray(res?.data)) {
                            return resolve({
                                success: false,
                                message: res.data[0].data
                            })
                        }
                    }

                    return resolve({
                        success: true,
                        podaci: res.data
                    })
                })
        })
    }

    public static sendIspitniRokId(data: IspitPrijavaRequest): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "ispit/getIspitRaspored", data)
                .then(res => {
                    if(res.status === "error") {
                        if(Array.isArray(res?.data)) {
                            return resolve({
                                success: false,
                                message: res.data[0].data
                            })
                        }
                    }

                    return resolve({
                        success: true,
                        podaci: res.data
                    })
                })
        })
    }

    public static ispitPrijava(data: IspitPrijavaRequest): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "ispit/prijavaIspita", data)
                .then(res => {
                    if(res.status === "error") {
                        if(Array.isArray(res?.data)) {
                            return resolve({
                                success: false,
                                message: res.data[0].data
                            })
                        }
                    }

                    return resolve({
                        success: true,
                        podaci: res.data
                    })
                })
        })
    }
}