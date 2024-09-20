import {withAuth} from "next-auth/middleware";
import {NextResponse} from "next/server";

export default withAuth(
    function middleware(req){
        if(req.nextauth.token.uloga !== "Profesor") {
            return NextResponse.rewrite(
                new URL("/", req.url)
            );
        }
    },
    {
        callbacks: {
            authorized: ({token}) => !!token
        }
    }
)

export const config = {
    matcher: ["/ispit/prijavljeniIspiti", "/ispit/zakljuceniIspiti", "/predmeti/predmetiForProfesor", "/profesor/profesorPodaci", "/profesor/promenaSifre",
        "/aktivnosti/aktivnosti"
    ]
}