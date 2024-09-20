import {api} from "../data/api";
import {PredmetRequest, IResults, PredmetPodaci, StudentPredmet} from "../types/Predmet";
import {useSession} from "next-auth/react";


export default class PredmetService {
    public static getPredmetForStudent(): Promise<StudentPredmet[] | null> {
        return new Promise<StudentPredmet[] | null>(async resolve => {
            await api("get", "predmet/getPredmetForStudent")
                .then(res => {
                    if(res.status !== "ok") {
                       return resolve(null);
                    }

                    resolve(res.data as StudentPredmet[]);
            })
        })
    }

    public static sendPredmetId(data: PredmetRequest): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "predmet/getAktivnost", data)
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