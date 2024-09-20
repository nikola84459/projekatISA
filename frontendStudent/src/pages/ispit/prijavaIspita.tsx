"use client"
import {useSession} from "next-auth/react";
import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Menu from "../../menu/Menu";
import Table from 'react-bootstrap/Table';
import {Alert, Container, Form, Modal} from "react-bootstrap";
import Header from "../../Header/Header";
import styles from "./prijavaIspita.module.css"
import Button from 'react-bootstrap/Button'
import PredmetService from "../../services/PredmetService";
import IspitService from "../../services/IspitService";
import {isIspitDetaljiPodaci, PrijavljeniPodaci} from "../../types/Ispit";
import StudentService from "../../services/StudentService";
import {PlacanjeResponse} from "../../types/Rata";
import useAuth from "../../hooks/useAuth";


export default function PrijavaIspitaPage() {
    const[predmeti, setPredmeti] = useState(null);
    const [stanjeNaRacunu, setStanjeNaRacun] = useState(0);
    const [prijavljeni, setPrijavljeni] = useState([]);
    const [ispitniRokovi, setIspitniRokovi] = useState(null);
    const [ispitDetalji, setIspitDetalji] = useState(null);
    const [pid, setPid] = useState(0);
    const [ispitniRokId, setIspitniRokId] = useState(null);
    const [alert, setAlert] = useState(false)
    const [message, setMessage] = useState(null);
    const [isPlacanje, setIsPlacanje] = useState(false);
    const [isPodaciOKartici, setPodaciOKartici] = useState(false);
    const [response, setResponse] = useState<PlacanjeResponse | null>(null);
    const [isOdgovor, setIsOdgovor] = useState(false);
    const [iznos, setIznos] = useState(0);
    const {data: session} = useSession();
    const [formData, setFormData] = useState({
        brojKartice: "",
        datumIsteka: "",
        cvv: ""
    })
    useAuth();


    useEffect( () => {
        const getPrijavljeni = async () => {
            return await IspitService.getPrijavljeniForStudent()
                .then(res => {
                    setPrijavljeni(res);
                })
            }

        const getStanje = async () => {
            return await StudentService.getStanje()
                .then(res => {
                    setStanjeNaRacun(res);
                })
        }

        const getPredmeti = async () => {
            return await PredmetService.getPredmetForStudent()
                .then(res  => {
                    setPredmeti(res);
                })
        }


        getPredmeti();
        getPrijavljeni();
        getStanje();

    }, [session]);


    const sendPredmetId = (e) => {
        setAlert(false);
        setMessage(false);
        const id = e.target.value;
        setPid(Number(e.target.value));

        IspitService.sendPredmetId({
            predmetId: id
        })
        .then(res => {
            if (res.success) {
                if(isIspitDetaljiPodaci(res.podaci)) {
                    setIspitDetalji(res.podaci);
                   return;
                }

                setIspitniRokovi(res.podaci)
            } else {
                setAlert(true);
                setMessage(res.message);
            }
        })
    }

    const close = () => {
        setIspitDetalji(null);
    }

    const closeIspitniRok = () => {
        setIspitniRokovi(null);
    }

    const sendIspitniRok = () => {
        setAlert(false);
        setMessage(false);
       IspitService.sendIspitniRokId({
           predmetId: pid,
           ispitniRokId: ispitniRokId
        })
        .then(res => {
            if(res.success) {
                setIspitniRokovi(null);
                setIspitDetalji(res.podaci);
            }
        })
    }


    const prijavaIspit = () => {
        IspitService.ispitPrijava({
            predmetId: Number(pid),
            ispitniRokId: Number(ispitDetalji.ispitniRokId)
        })
        .then(res => {
            if(res.success) {
                IspitService.getPrijavljeniForStudent()
                .then(res => {
                    setPrijavljeni(res)

                    StudentService.getStanje()
                        .then(res => {
                            setStanjeNaRacun(res);
                        })
                })
            } else {
                setAlert(true);
                setMessage(res.message);
            }

            setIspitDetalji(null);
        })
    }

    const setIsPodaciOKartici = () => {
        if(iznos > 0 || iznos === null) {
            setPodaciOKartici(true);
            setIsPlacanje(false)
        }
    }

    const uzmiPodatke = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        })
    }

    const zatvori = () => {
        setPodaciOKartici(false);
        setIsPlacanje(false);
        console.log("zdravo")
    }


    const plati = () => {
        StudentService.updateStanje({
            iznos: Number(iznos),
            brojKartice: formData.brojKartice,
            datumIsteka: formData.datumIsteka,
            cvv: formData.cvv
        })
        .then(res => {
            if(res.success) {
                StudentService.getStanje()
                .then(res => {
                    setStanjeNaRacun(res);
                })
                setResponse(res.podaci);
                setIsOdgovor(true);
                setIsPlacanje(false);
                setPodaciOKartici(false);
            } else {
                setResponse(res.podaci);
                setIsOdgovor(true);
                setIsPlacanje(false);
                setPodaciOKartici(false);
            }
        })
    }

    return (
        <>
            <Header title={"Prijava ispita"} />
            <div className={styles.menu}>
                <Menu />
            </div>

            <Container className={styles.container}>
                <h4>Stanje na računu: {stanjeNaRacunu !== null ? stanjeNaRacunu.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.'): "0.00"} <Button style={{textDecoration: "none"}} onClick={() => setIsPlacanje(true)} variant="link">PLATI ISPIT</Button></h4>

                <>
                    <>
                        <Alert show={alert} className={styles.alert} variant="danger">{message}</Alert>
                    </>
                    <Table className={styles.tabela} responsive="sm">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Naziv predmeta</th>
                                <th>Akronim</th>
                                <th>ESPB</th>
                                <th>Profesor</th>

                            </tr>
                        </thead>
                        <tbody>
                        {predmeti?.map(predmet => (
                            <tr>
                                <td><Button onClick={e => {sendPredmetId(e)}} value={predmet.predmet.predmetId} variant="primary">Prijavi</Button></td>
                                <td>{predmet.predmet.naziv}</td>
                                <td>{predmet.predmet.akronim}</td>
                                <td>{predmet.predmet.espb}</td>
                                <td>{predmet.predmet.profesor.ime + " " + predmet.predmet.profesor.prezime}</td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                </>

                <div className={styles.prijavljeni}>
                    <h4>Prijavljeni ispiti</h4>
                    {
                       prijavljeni !== null ? (
                    <Table responsive="sm">
                        <thead>
                        <tr>
                           <th>Naziv predmeta</th>
                            <th>Ispitni rok</th>
                            <th>Akronim</th>
                            <th>Profesor</th>
                            <th>Ocena</th>

                        </tr>
                        </thead>
                        <tbody>
                        {prijavljeni?.map(prijavljen => (
                            <tr>
                                <td>{prijavljen?.predmet?.naziv}</td>
                                <td>{prijavljen?.ispitniRok?.naziv}</td>
                                <td>{prijavljen?.predmet?.akronim}</td>
                                <td>{prijavljen?.predmet?.profesor?.ime + " " + prijavljen?.predmet?.profesor?.prezime}</td>
                                <td>{prijavljen?.ispit?.izasao || prijavljen?.ispit == null ? prijavljen?.ispit?.ocena : "NI"}</td>
                                <td></td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                        ):
                        <Alert key="info" variant="info">Niste prijavili ni jedan ispit</Alert>
                    }
                </div>
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
                        <Button onClick={() => sendIspitniRok()} className={styles.dugmeIspitniRok} variant="primary">Potvrdi</Button>
                        <Button className={styles.dugmeOdustani} onClick={() => closeIspitniRok()} variant="danger">Odustani</Button>
                        </Modal.Body>

                </Modal>
                <Modal

                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={ispitDetalji !== null}
                >
                    <Modal.Header onClick={() => {close()}} closeButton>
                        <Modal.Title id="contained-modal-title-vcenter">
                            Podaci prijave
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                <Form.Label>IspitniRok</Form.Label>
                                <Form.Control disabled={true} type="text" value={ispitDetalji?.ispitniRokNaziv}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                <Form.Label>Predmet naziv</Form.Label>
                                <Form.Control disabled={true} type="text" value={ispitDetalji?.predmetNaziv}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                <Form.Label>Vreme ispita</Form.Label>
                                <Form.Control disabled={true} type="text" value={ispitDetalji?.vremeIspita}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                <Form.Label>Cena prijava</Form.Label>
                                <Form.Control disabled={true} type="text" value={ispitDetalji?.cenaPrijave}/>
                            </Form.Group>
                            {
                                !ispitDetalji?.redovnaPrijava ? (
                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                <Form.Label>Obrazloženje cene</Form.Label>
                                <Form.Control disabled={true} type="text" as="textarea" value="Student prijavljuje ispit van termina prijave."/>
                            </Form.Group>
                                ):
                                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                    <Form.Label>Obrazloženje cene</Form.Label>
                                    <Form.Control disabled={true} type="text" as="textarea" value=""/>
                                </Form.Group>
                            }
                            <Button onClick={() => prijavaIspit()} variant="primary">Potvrdi</Button>
                            <Button className={styles.dugmeOdustani} onClick={() => close()} variant="danger">Odustani</Button>
                        </Form>
                    </Modal.Body>
                </Modal>
                <Modal
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={isPlacanje}
                >
                    <Modal.Header>
                        <Modal.Title>Plaćanje ispita</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                <Form.Label>Iznos</Form.Label>
                                <Form.Control onChange={e => setIznos(Number(e.target.value))} type="text" placeholder="Unesite željeni iznos"/>
                            </Form.Group>
                        </Form>
                        <Button onClick={() => setIsPodaciOKartici()} variant="primary">Potvrdi</Button>
                        <Button className={styles.dugmeOdustaniPlacanje} onClick={() => setIsPlacanje(false)} variant="danger">Odustani</Button>
                    </Modal.Body>
                </Modal>
                <Modal
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={isPodaciOKartici && iznos > 0}
                >
                    <Modal.Header onClick={() => zatvori()} closeButton>
                        <Modal.Title>Plaćanje - Molimo Vas unesite podatke sa platne kartice</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group className="mb-3">
                                <Form.Label>Broj kartice</Form.Label>
                                <Form.Control onChange={e => {uzmiPodatke(e)}} name="brojKartice" type="text" placeholder="Unesite broj Vaše kartice" />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Datum isteka kartice</Form.Label>
                                <Form.Control onChange={e => {uzmiPodatke(e)}} name="datumIsteka" type="text" placeholder="mm/gg" />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Cvv</Form.Label>
                                <Form.Control onChange={e => {uzmiPodatke(e)}} name="cvv" type="password" placeholder="Unesite cvv kod sa Vaše platne kartice" />
                            </Form.Group>

                            <Button onClick={e => plati()} variant="primary">Plati</Button>
                            <Button className={styles.dugmeOdustani} onClick={() => zatvori()} variant="danger">Otkaži</Button>
                        </Form>
                    </Modal.Body>
                </Modal>
                <Modal
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={isOdgovor}
                >
                    <Modal.Header>
                        <Modal.Title>Poruka</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Table>
                            <tbody>
                            <tr>
                                <th>Status:</th>
                                <td style={response?.uspesno ? ({color: "green"}) : {color: "red"}}>{response?.uspesno ? ("Uspešno"): "Neuspešno"}</td>
                            </tr>
                            <tr>
                                <th>Broj transakcije:</th>
                                <td>{response?.brojTransakcije}</td>
                            </tr>
                            <tr>
                                <th>Poruka</th>
                                <td>{response?.uspesno ? ("Plaćanje je uspešno izvršeno račun Vaše platne kartice je zadužen"): response?.poruka}</td>
                            </tr>
                            </tbody>
                        </Table>
                        <Button onClick={() => setIsOdgovor(false)} variant="danger">Zatvori</Button>
                    </Modal.Body>
                </Modal>
            </Container>
        </>
    )
}