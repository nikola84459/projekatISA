import Header from "../../Header/Header";
import Menu from "../../Menu/Menu";
import {Container, Table} from "react-bootstrap";
import styles from "./profesor.module.css"
import {useEffect, useState} from "react";
import ProfesorService from "../../services/ProfesorService";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";

export default function ProfesorPodaci() {
    const [profesor, setProfesor] = useState(null);
    const {data: session} = useSession();

    useAuth();

    useEffect(() => {
        ProfesorService.getProfesorData()
            .then(res => {
                setProfesor(res);
            })
    }, [session]);

    console.log(profesor);
    return (
        <>
            <Header title="Lični podaci" />
            <Menu />
            <Container className={styles.container}>
                <Table className={styles.podaciTabela} responsive="sm">
                    <tbody>
                    <tr>
                        <th>ime</th>
                        <td>{profesor?.ime}</td>
                    </tr>
                    <tr>
                        <th>Prezime</th>
                        <td>{profesor?.prezime}</td>
                    </tr>
                    <tr>
                        <th>Korisničko ime</th>
                        <td>{profesor?.kime}</td>
                    </tr>
                    <tr>
                        <th>E-mail</th>
                        <td>{profesor?.email}</td>
                    </tr>
                </tbody>
                </Table>
            </Container>
        </>
    )
}