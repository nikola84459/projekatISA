import {RequestPredmetId} from "../types/Predmet";
import {api} from "../api/api";
import {RequestGetPrijava, IResults, RequestAddIspit, RequestZakljuci} from "../types/Ispit";
import {IResultsIspitniRok} from "../types/IspitniRok";

export default class IspitService {
    public static sendPredmetId(data: RequestPredmetId): Promise<IResultsIspitniRok> {
        return new Promise<IResultsIspitniRok>(resolve => {
            api("post", "ispit/getNezakljuceniIspitniRok", data)
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

    public static getPrijava(data: RequestGetPrijava): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "ispit/getPrijavljeni", data)
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

    public static addIspit(data: RequestAddIspit): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "ispit/addIspit", data)
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

    public static setIsZakljucen(data: RequestZakljuci): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "ispit/setIsZakljucen", data)
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

    public static sendPredmetIdZakljuceni(data: RequestPredmetId): Promise<IResultsIspitniRok> {
        return new Promise<IResultsIspitniRok>(resolve => {
            api("post", "ispit/getZakljucenIspitniRok", data)
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

    public static getZakljuceni(data: RequestGetPrijava): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "ispit/getZakljuceniForProfesor", data)
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