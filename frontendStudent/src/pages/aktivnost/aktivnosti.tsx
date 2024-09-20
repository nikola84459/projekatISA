"use client"
import Header from "../../Header/Header";
import Menu from "../../menu/Menu";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container} from "react-bootstrap";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import styles from "./aktivnost.module.css"
import {AktivnostPodaci, PredmetPodaci, StudentPredmet} from "../../types/Predmet";
import {useEffect, useState} from "react";
import PredmetService from "../../services/PredmetService";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";



export default function aktivnosti() {
    const[predmeti, setPredmeti] = useState(null);
    const [aktivnost, setAktivnost] = useState(null);
    const {data: session} = useSession();
    useAuth();

    useEffect(() => {
        const getPredmet = async () => {
            await PredmetService.getPredmetForStudent()
                .then(res  => {
                    setPredmeti(res);
                })
        }

        getPredmet();

    }, [session]);

    const getAktivnost = (e) => {
        const id = e.target.value;

        setAktivnost(
            predmeti.find(p => p.predmet.predmetId === Number(id))
        )

    }

    return (
        <>
            <Header title={"Aktivnosti"} />
            <div>
                <Menu />
            </div>
            <Container className={styles.container}>
                <Table>
                    <thead>

                    <tr>
                        <th>Naziv predmeta</th>
                        <th></th>
                    </tr>
                    </thead>
                   <tbody>
                   {
                       predmeti?.map(predmet => (
                            <tr>
                                <td>{predmet.predmet.akronim + " " + predmet.predmet.naziv}</td>
                                <td><Button onClick={e => {getAktivnost(e)}} value={predmet.predmet.predmetId} variant="primary">Detalji</Button></td>
                            </tr>
                   ))}
                   </tbody>
                </Table>
                {
                    aktivnost !== null ? (
                <div className={styles.aktivnosti}>
                    <h3>Pregled aktivnosti</h3>
                    <Table className={styles.aktivnostiTabela}>
                        <tbody>
                            <tr>
                                <th>Aktivnost:</th>
                                <td>{aktivnost.aktivnost}</td>
                            </tr>
                            <tr>
                                <th>Prvi kolokvijum:</th>
                                <td>{aktivnost.prviKolokvijum}</td>
                            </tr>
                            <tr>
                                <th>Drugi kolokvijum:</th>
                                <td>{aktivnost.drugiKolokvijum}</td>
                            </tr>
                        </tbody>
                    </Table>
                </div>
                    ): null}
            </Container>
        </>
    )
}