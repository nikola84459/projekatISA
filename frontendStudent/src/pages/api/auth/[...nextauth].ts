import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import StudentService from "../../../services/StudentService";


export default NextAuth({
    providers: [
         CredentialsProvider({
            name: "Credentials",
            credentials: {
                username: { label: "Korisničko ime", type: "text" },
                password: {  label: "Lozinka", type: "password" },
            },
             //@ts-ignore
             authorize: async function (credentials, req) {
                 const res = await StudentService.logIn({
                     username: credentials.username,
                     password: credentials.password
                 })

                 if (res.success) {
                     return Promise.resolve(res.podaci)
                 } else {
                     throw new Error(res.message);
                 }

             }
         })
    ],

    callbacks: {
        async jwt({token, user, trigger, session}) {
            if(trigger === "update") {
               return{...token, ...session.user}
            }

            return { ...token, ...user };

        },

        async session({session, token}) {
            session.user = token as any;
            return session;
        }
    },
    pages: {
        signIn: "/"
    },
})