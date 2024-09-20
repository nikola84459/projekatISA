import {api} from "../api/api";
import {AktivnostPodaci, AktivnostRequest, IResults, PredmetPodaci, RequestPredmetId} from "../types/Predmet";

export default class PredmetService {
    public static getPredmetForProfesor(): Promise<PredmetPodaci[] | null> {
        return new Promise<PredmetPodaci[] | null>(resolve => {
            api("get", "predmet/getPredmetForProfesor")
                .then(res => {
                    if (res.status !== "ok") {
                        return resolve(null);
                    }

                    resolve(res.data as PredmetPodaci[]);
                })
        })
    }

    public static sendPredmetId(data: RequestPredmetId): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "predmet/getUpisani", data)
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

    public static addAktivnost(data: AktivnostRequest): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "predmet/addAktivnost", data)
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
                    })
                })
        })
    }

}