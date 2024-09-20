"use client"
import Header from "../../Header/Header";
import Menu from "../../menu/Menu";
import {Container} from "react-bootstrap";
import styles from "./student.module.css"
import Table from "react-bootstrap/Table";
import {useEffect, useState} from "react";
import StudentService from "../../services/StudentService";
import {StudentPodaci} from "../../types/Student";
import Link from "next/link";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";
import Obavestenja from "../obavestenja";
import authenticated from "../../middleware";


export default function StudentPodaci() {
    const [student, setStudent] = useState(null);
    const {data: session} = useSession();
    useAuth();

    useEffect(() => {
        const getPodaci = async () => {
            await StudentService.getStudent()
                .then(res => {
                    setStudent(res);
                })
        }
        getPodaci();
    }, [session]);

    return (
        <>
            <Header title={"Lični podaci"} />
            <Menu />
            <Container className={styles.container}>
                <Table className={styles.podaciTabela} responsive="sm">
                    <tbody>
                        <tr>
                            <th>ime</th>
                            <td>{student?.ime}</td>
                        </tr>
                    <tr>
                        <th>Prezime</th>
                        <td>{student?.prezime}</td>
                    </tr>
                    <tr>
                        <th>Broj indeksa</th>
                        <td>{student?.brIndeksa}</td>
                    </tr>
                    <tr>
                        <th>JMBG</th>
                        <td>{student?.jmbg}</td>
                    </tr>
                    <tr>
                        <th>E-mail</th>
                        <td>{student?.email}</td>
                    </tr>
                    <tr>
                        <th>Stanje na računu</th>
                        <td>{student?.stanjeNaRacunu}</td>
                    </tr>
                    <tr>
                        <th>Šifra</th>
                        <td>
                            <Link href={"./student/promenaSifre"}>Promenite šifru</Link>
                        </td>
                    </tr>
                    </tbody>
                </Table>
            </Container>
        </>
    )
}