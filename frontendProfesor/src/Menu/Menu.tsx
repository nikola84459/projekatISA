import {Nav, Navbar} from "react-bootstrap";
import Link from "next/link";
import 'bootstrap/dist/css/bootstrap.min.css';
import ProfesorService from "../services/ProfesorService";
import {signOut} from "next-auth/react";
import {useRouter} from "next/router";

export default function Menu() {

    const router = useRouter();
    const logout = async () => {
        await ProfesorService.logout()
            .then(async res => {
                if(res) {
                    await signOut()
                    await router.push("/");
                }
            })
    }

    return (
        <>
            <Navbar bg="primary" data-bs-theme="dark">
                <Nav className="me-auto">
                    <Nav.Link as={Link} href="../predmeti/predmetiForProfesor">Predmeti</Nav.Link>
                    <Nav.Link as={Link} href="../profesor/profesorPodaci">Moji podaci</Nav.Link>
                    <Nav.Link as={Link} href="../profesor/promenaSifre">Promena šifre</Nav.Link>
                    <Nav.Link onClick={logout} as={Link} href="#">Odjava</Nav.Link>
                </Nav>
            </Navbar>
        </>
    )
}