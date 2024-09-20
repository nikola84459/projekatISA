"use client";
import {getSession, useSession} from "next-auth/react";
import {useEffect, useState} from "react";
import {axiosInstance, refreshToken} from "../data/api";
import {useRefreshToken} from "./useRefresh";

const useAuth = async () => {
    const { data: session } = useSession();
    const refreshToken = useRefreshToken();

    useEffect(() => {
        if(session && session.user) {
            const requestInterceptor = axiosInstance.interceptors.request.use(
            (config) => {
                if (!config.headers["Authorization"]) {
                    //@ts-ignore
                    config.headers["Authorization"] = `Bearer ${session.user.accessToken}`;
                    //@ts-ignore
                    console.log(session.user.accessToken)
                }
                return config;
            },
            (error) => Promise.reject(error)
        );

            const responseIntercept = axiosInstance.interceptors.response.use(
            (response: any) => response,
            async (error: any) => {
                const prevRequest = error?.config;
                if ((error?.response?.status === 401 || error?.response?.status === 403) && !prevRequest?.sent) {
                    prevRequest.sent = true;

                    const noviAccessToken = await refreshToken();

                    //@ts-ignore
                    prevRequest.headers["Authorization"] = `Bearer ${noviAccessToken.accessToken}`;

                    return axiosInstance(prevRequest);
                }

                return Promise.reject(error);
            }
        );

            return () => {
                axiosInstance.interceptors.request.eject(requestInterceptor);
                axiosInstance.interceptors.response.eject(responseIntercept);
            };
        }
    }, [session, refreshToken]);

    return axiosInstance;
};

export default useAuth;