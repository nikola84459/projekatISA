import {signIn, useSession} from "next-auth/react";
import {refreshToken} from "../data/api";

export const useRefreshToken = () => {
    const {data: session, update} = useSession();

    const refresh = async () => {
         refreshToken().
         then(async res => {
             if(session) {
                 await update({
                   ...session,
                 user: {
                   ...session?.user,
                 //@ts-ignore
                 accessToken: res.accessToken,
                 //@ts-ignore
                 refreshToken: res.refreshToken
                 },
             });

                 //@ts-ignore
                 return {accessToken: res.accessToken};
             } else {
                 console.log("ovdeee else");
                 await signIn();
             }
         })
    }

    return refresh;
}

