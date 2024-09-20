import {api} from "../api/api";
import {IResults, IResultsLogin, ProfesorPodaci, RequestLogin, RequestSifra} from "../types/Profesor";

export default class ProfesorService {
    public static getProfesorData(): Promise<ProfesorPodaci | null> {
        return new Promise<ProfesorPodaci | null>(resolve => {
            api("get", "profesor/getById")
                .then(res => {
                    if (res.status !== "ok") {
                        return resolve(null);
                    }

                    resolve(res.data as ProfesorPodaci);
                })
        })
    }

    public static promenaSifre(data: RequestSifra): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "profesor/promenaSifreProfesor", data)
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

    public static logIn(data: RequestLogin): Promise<IResultsLogin> {
        return new Promise<IResultsLogin>(resolve => {
            api("post", "auth/loginProfesor", data)
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

    public static logout(): Promise<boolean> {
        return new Promise<boolean>(async resolve => {
            await api("get", "auth/logout")
                .then(res => {
                    if(res.status !== "ok") {
                        return resolve(false);
                    }

                    resolve(true);
                })
        })
    }
}