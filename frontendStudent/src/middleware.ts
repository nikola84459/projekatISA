import {withAuth} from "next-auth/middleware";
import {NextResponse} from "next/server";

export default withAuth(
    function middleware(req){
       if(req.nextauth.token.uloga !== "Student") {
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
    matcher: ["/aktivnost/aktivnost", "/ispit/prijavaIspita", "/ispit/uspesnoPolozeni", "/ispit/neuspesnaPolaganja", "/obavestenja/", "/obavestenja/staraObavestenja",
        "/rata/skolarineIUplate", "/student/", "/student/promenaSifre", "/upisi/"
    ]
}