import {api} from "../data/api";
import {IResultsLogin, PromenaSifreRequest, RequestLogin, StudentPodaci, UpdateStanjeNaRacunu} from "../types/Student";
import {IResults} from "../types/Rata";
import {IResult} from "../types/Student";
import {useSession} from "next-auth/react";

export default class StudentService {
    public static getStudent(): Promise<StudentPodaci | null> {
        return new Promise<StudentPodaci | null>(resolve => {
            api("get", "student/getStudentById")
                .then(res => {
                    if(res.status !== "ok") {
                        return resolve(null);
                    }

                    resolve(res.data as StudentPodaci);
                })
        })
    }

    public static getStanje(): Promise<number | null> {
        return new Promise<number | null>(async resolve => {
            await api("get", "student/getStanjeNaRacunu")
                .then(res => {
                    if(res.status !== "ok") {
                       return resolve(null);
                    }

                    resolve(res.data);
                })
        })
    }

    public static updateStanje(data: UpdateStanjeNaRacunu): Promise<IResults> {
        return new Promise<IResults>(resolve => {
            api("post", "student/updateStanjeNaRacunu", data)
                .then(res => {
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

    public static promenaSifre(data: PromenaSifreRequest): Promise<IResult> {
        return new Promise<IResult>(resolve => {
            api("post", "student/promenaSifreStudent", data)
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
            api("post", "auth/loginStudent", data)
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
            console.log("ovde sam sada ja.");
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