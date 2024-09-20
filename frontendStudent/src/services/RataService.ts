import {api} from "../data/api";
import {RataPlacanjeRequest, RataPodaci, IResults} from "../types/Rata";
import {useSession} from "next-auth/react";


export default class RataService {
    public static async getRata(): Promise<RataPodaci | null> {
        return new Promise<RataPodaci | null>(async resolve => {
            await api("get", "rata/getAllForStudent")
                .then(async res => {
                    if(res.status !== "ok") {
                      return resolve(null);
                    }

                    return resolve(res.data as RataPodaci);
                })
        })
    }

    public static plati(data: RataPlacanjeRequest): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "rata/platiSkolarinu", data)
                .then(res => {
                    console.log("ovdee");
                    if(res.status === "error") {
                        if(Array.isArray(res?.data)) {
                            return resolve({
                                success: false,
                                podaci: res.data[0].data
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