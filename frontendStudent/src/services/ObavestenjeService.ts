import {api} from "../data/api";
import {ObavestenjePodaci} from "../types/Obavestenje";
import {useSession} from "next-auth/react";

export default class ObavestenjeService {
    public static async getObavestenje(): Promise<ObavestenjePodaci[] | null> {
        return new Promise<ObavestenjePodaci[] | null>(async resolve => {
           await api("get", "obavestenje/getObavestenje")
                .then(res => {
                    if(res.status !== "ok") {
                       return resolve(null);
                    }

                    return resolve(res.data as ObavestenjePodaci[]);
                })
        })
    }

    public static async getStaraObavestenja(): Promise<ObavestenjePodaci[] | null> {
        return new Promise<ObavestenjePodaci[] | null>(async resolve => {
            await api("get", "obavestenje/getStaroObavestenje")
                .then(res => {
                    if(res.status !== "ok") {
                       return resolve(null);
                    }
                    return resolve(res.data as ObavestenjePodaci[]);
                })
        })
    }
}