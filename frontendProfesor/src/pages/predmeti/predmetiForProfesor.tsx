import Header from "../../Header/Header";
import Menu from "../../Menu/Menu";
import {Alert, Container, Form, Modal, Table} from "react-bootstrap";
import styles from "./predmeti.module.css"
import React, {createContext, useEffect, useState} from "react";
import PredmetService from "../../services/PredmetService";
import Button from "react-bootstrap/Button";
import Link from "next/link";
import IspitService from "../../services/IspitService";
import {useRouter} from "next/router";
import useAuth from "../../hooks/useAuth";
import {useSession} from "next-auth/react";



export default function PredmetiForProfesor() {
   const [predmeti, setPredmeti] = useState(null);
   const [ispitniRokovi, setIspitniRokovi] = useState(null);
   const [ispitniRokoviZakljuceni, setIspitniRokoviZakljuceni] = useState(null);
   const [predmetId, setPredmetId] = useState(null);
   const [ispitniRokId, setIspitniRokId] = useState(null);
   const [poruka, setPoruka] = useState(null);
   const router = useRouter();
    const {data: session} = useSession();

   useAuth();

    useEffect(() => {
        PredmetService.getPredmetForProfesor()
            .then(res  => {
                setPredmeti(res);
        })
    }, [session]);

    const closeIspitniRok = () => {
        setIspitniRokovi(null);
    }

    const closeIspitniRokZakljuceni = () => {
        setIspitniRokoviZakljuceni(null);
    }

    const sendPredmetId = (e) => {
        const id = e.target.value;
        setPredmetId(id);

        IspitService.sendPredmetId({
            predmetId: id
        })
            .then(res => {
                if(res.success) {
                    setPoruka(null);
                    setIspitniRokovi(res.podaci);
                } else {
                    setPoruka(res.message)
                }
            })
    }

    const sendPredmetIdZakljuceni = (e) => {
        const id = e.target.value;
        setPredmetId(id);

        IspitService.sendPredmetIdZakljuceni({
            predmetId: id
        })
        .then(res => {
            if(res.success) {
                setIspitniRokoviZakljuceni(res.podaci)
            } else {
                setPoruka(res.message)
            }
        })
    }

   return (
        <>
            <Header title="Predmeti"/>
            <Menu />
            <Container className={styles.container}>
                <Alert show={poruka !== null} variant="danger">{poruka}</Alert>
                <Table>
                    <thead>
                        <tr>
                            <th>Naziv</th>
                            <th>Akronim</th>
                            <th>ESPB</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        predmeti?.map(predmet => (
                        <tr>
                            <td>{predmet?.naziv}</td>
                            <td>{predmet?.akronim}</td>
                            <td>{predmet?.espb}</td>
                            <td><Button onClick={(e) => sendPredmetIdZakljuceni(e)} value={predmet.predmetId} size="sm" variant="primary">Pregled zaključenih ispita</Button></td>
                            <td><Link href={{pathname:"../aktivnosti/aktivnosti", query: {predmetId: predmet.predmetId}}}>
                                <Button size="sm" variant="primary">Studenti i aktivnosti</Button>
                            </Link>
                            </td>
                            <td><Button onClick={(e) => sendPredmetId(e)} value={predmet.predmetId} size="sm" variant="primary">Unos ispita</Button></td>
                        </tr>
                        ))}
                    </tbody>
                </Table>
                <Modal
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={ispitniRokovi !== null}
                >
                    <Modal.Header onClick={() => {closeIspitniRok()}} closeButton>
                        <Modal.Title id="contained-modal-title-vcenter">
                            Molimo Vas izaberite ispitni rok
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form.Select onChange={(e) => setIspitniRokId(Number(e.target.value))} aria-label="Default select example">
                            <option>Izaberite ispitni rok</option>
                            {
                                ispitniRokovi?.map(i => (
                                    <option value={i.ispitniRokId}>{i.naziv}</option>
                                ))}
                        </Form.Select>
                        <Link href={{pathname:"../ispit/prijavljeniIspiti", query: {predmetId: predmetId, ispitniRokId: ispitniRokId}}}>
                            <Button  className={styles.dugmeIspitniRok} variant="primary">Potvrdi</Button>
                        </Link>
                        <Button className={styles.dugmeOdustani} onClick={() => closeIspitniRok()} variant="danger">Odustani</Button>
                    </Modal.Body>
                </Modal>
                <Modal
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={ispitniRokoviZakljuceni !== null}
                >
                    <Modal.Header onClick={() => {closeIspitniRokZakljuceni()}} closeButton>
                        <Modal.Title id="contained-modal-title-vcenter">
                            Molimo Vas izaberite ispitni rok
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form.Select onChange={(e) => setIspitniRokId(Number(e.target.value))} aria-label="Default select example">
                            <option>Izaberite ispitni rok</option>
                            {
                                ispitniRokoviZakljuceni?.map(i => (
                                    <option value={i.ispitniRokId}>{i.naziv}</option>
                                ))}
                        </Form.Select>
                        <Link href={{pathname:"../ispit/zakljuceniIspiti", query: {predmetId: predmetId, ispitniRokId: ispitniRokId}}}>
                            <Button  className={styles.dugmeIspitniRok} variant="primary">Potvrdi</Button>
                        </Link>
                        <Button className={styles.dugmeOdustani} onClick={() => closeIspitniRokZakljuceni()} variant="danger">Odustani</Button>
                    </Modal.Body>
                </Modal>
            </Container>
        </>
    )
}