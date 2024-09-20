"use client"
import Header from "../../Header/Header";
import Menu from "../../menu/Menu";
import {Container, Modal} from "react-bootstrap";
import styles from "./prijavaIspita.module.css"
import Table from "react-bootstrap/Table";
import {ZakljuceniPodaci} from "../../types/Ispit";
import {useEffect, useState} from "react";
import IspitService from "../../services/IspitService";
import Button from "react-bootstrap/Button";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";

export default function uspesnoPolozeni() {
    const [ispiti, setIspiti] = useState(null);
    const [prikazAktivnost, setPrikazAktivnost] = useState(false);
    const [aktivnost, setAktivnost] = useState(null);
    const {data: session} = useSession();

    useAuth();

    useEffect(() => {
        console.log(session);
        const getNePolozeni = async () => {
            await IspitService.getNePolozeni()
                .then(res => {
                    setIspiti(res);
                })
        }

        getNePolozeni();

    }, [session]);
    const aktivnosti = (e) => {
        const id = e.target.value;
        const trazeniIspit = ispiti.find(ispit => ispit.ispitPrijavaId === Number(id));

        setAktivnost(trazeniIspit);
        setPrikazAktivnost(true);
    }

    const close = () => {
        setPrikazAktivnost(false);
    }

    return (
        <>
            <Header title={"Neuspešna polaganja"} />
            <Menu />
            <Container className={styles.container}>
                <Table>
                    <thead>
                    <tr>
                        <th>Naziv predmeta</th>
                        <th>Akronim</th>
                        <th>ESPB</th>
                        <th>Profesor</th>
                        <th>Ispitni rok</th>
                        <th>Bodovi</th>
                        <th>Ocena</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        ispiti?.map(ispit => (
                            <tr>
                                <td>{ispit?.studentPredmet?.predmet?.naziv}</td>
                                <td>{ispit?.studentPredmet?.predmet?.akronim}</td>
                                <td>{ispit?.studentPredmet?.predmet?.espb}</td>
                                <td>{ispit?.studentPredmet?.predmet?.profesor?.ime + " " + ispit?.studentPredmet?.predmet?.profesor?.prezime}</td>
                                <td>{ispit?.ispitniRok.naziv}</td>
                                <td>{ispit?.studentPredmet?.aktivnost + ispit?.studentPredmet.prviKolokvijum + ispit?.studentPredmet?.drugiKolokvijum + ispit.ispit.bodovi}</td>
                                <td>{ispit?.ispit.ocena}</td>
                                <td><Button onClick={(e) => {aktivnosti(e)}} value={ispit.ispitPrijavaId} variant="primary">Aktivnosti</Button></td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
                <Modal

                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={prikazAktivnost}
                >
                    <Modal.Header onClick={() => {close()}} closeButton>
                        <Modal.Title id="contained-modal-title-vcenter">
                            Pregled aktivnosti
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div>
                            <h4>Predispitne aktivnosti</h4>
                            <Table>
                                <tbody>
                                <tr>
                                    <th>Aktivnost:</th>
                                    <td>{aktivnost?.studentPredmet.aktivnost}</td>
                                </tr>
                                <tr>
                                    <th>Prvi kolokvijum:</th>
                                    <td>{aktivnost?.studentPredmet.prviKolokvijum}</td>
                                </tr>
                                <tr>
                                    <th>Drugi kolokvijum:</th>
                                    <td>{aktivnost?.studentPredmet.drugiKolokvijum}</td>
                                </tr>
                                </tbody>
                            </Table>
                            <h4>Ispitne aktivnosti</h4>
                            <Table>
                                <tbody>
                                <tr>
                                    <th>Završni ispit:</th>
                                    <td>{aktivnost?.ispit?.bodovi}</td>
                                </tr>
                                </tbody>
                            </Table>
                        </div>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={() => {close()}} variant="primary">Zatvori</Button>
                    </Modal.Footer>
                </Modal>
            </Container>
        </>
    )
}