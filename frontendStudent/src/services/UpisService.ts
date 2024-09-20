import {api} from "../data/api";
import {UpisPodaci} from "../types/Upis";
import {useSession} from "next-auth/react";

export default class UpisService {
    public static getUpis(): Promise<UpisPodaci[] | null | "login"> {
        return new Promise<UpisPodaci[] | null | "login">(resolve => {
            api("get", "student/getUpis")
                .then(res => {
                    if(res.status !== "ok") {
                        if(res.status === "login") {
                            return resolve("login")
                        }
                        return resolve(null);
                    }

                    resolve(res.data as UpisPodaci[]);
                })
        })
    }
}