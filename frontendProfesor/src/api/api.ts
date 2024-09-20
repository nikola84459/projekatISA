import axios, {AxiosResponse} from "axios";
import { getSession } from "next-auth/react";


type ApiMethod = "get" | "post" | "put" | "delete";
type ApiResponseStatus =  "ok" | "error" | "login";
export interface ApiResponse {
    status: ApiResponseStatus,
    data: any
}

export const  axiosInstance = axios.create({
    baseURL: "http://localhost:8080/",
    headers: {
        'Content-Type': 'application/json;charset=UTF-8',
    },
});
export async function api(method: ApiMethod, path: string, body: any | undefined = undefined): Promise<ApiResponse> {

    return new Promise<ApiResponse>(async resolve => {

        await axiosInstance({
            method: method,
            baseURL: "http://localhost:8080/",
            url: path,
            data: body ? JSON.stringify(body) : "",
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',


            }
        }).then(res => {
            responseHendler(res, resolve)
        })
            .catch(async err => {
                return resolve ({
                    status: "error",
                    data: [err?.response]
                });
            })

    })
}

function responseHendler(res: AxiosResponse<any>, reslove: (data: ApiResponse) => void){
    if(res?.status < 200 || res?.status > 300) {
        return reslove(
            {
                status: "error",
                data: "" + res.data
            }
        );
    }

    return reslove({
        status: "ok",
        data: res.data
    })
}

export function refreshToken(): Promise<ApiResponse> {
    return new Promise<ApiResponse>(async reslove => {
        const session = await getSession();
        //@ts-ignore
        const refreshToken = session?.user?.refreshToken;

        axios({
            method: "post",
            baseURL: "http://localhost:8080",
            url: "auth/refresh_token",
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                ...(refreshToken !== undefined) ?
                    {'Authorization': `Bearer ${refreshToken}`} : ""

            }
        })
            .then(res => {
                refreshTokenResponseHendler(res, reslove);
            })
            .catch(() => {
                reslove(null);
            })
    })
}

function refreshTokenResponseHendler(res: AxiosResponse<any>, reslove: (data: ApiResponse | null) => void) {
    if(res.status !== 200) {
        reslove(null)
    }
    reslove(res.data);
}
