"use client"
import {useSession} from "next-auth/react";
import Header from "../../Header/Header";
import Menu from "../../menu/Menu";
import styles from "./rata.module.css"
import {Alert, Container, Form, Modal} from "react-bootstrap";
import Table from "react-bootstrap/Table";
import {useEffect, useState} from "react";
import RataService from "../../services/RataService";
import Button from "react-bootstrap/Button";
import {PlacanjeResponse} from "../../types/Rata";
import useAuth from "../../hooks/useAuth";


export default function Rata() {
    const {data: session} = useSession();
    const [rata, setRata] = useState(null);
    const [platiSve, setPlatiSve] = useState(false)
    const [oznaceneRata, setOznaceneRate] = useState([]);
    const [placanje, setPlacanje] = useState(false)
    const [rataIds, setRataIds] = useState<number[]>([]);
    const [isOdgovor, setIsOdgovor] = useState(false);
    const [response, setResponse] = useState<PlacanjeResponse | null>(null);
    const [iznos, setIznos] = useState(0);
    const [isOznacene, setIsOznacene] = useState(false);
    const [formData, setFormData] = useState({
        brojKartice: "",
        datumIsteka: "",
        cvv: ""
    })
    useAuth();

    useEffect(() => {
        //console.log(session);
         const getRate = async () => {
            return await RataService.getRata()
                .then(async res => {
                    setRata(res);
                })

        }

        getRate();
    }, [session]);

    const oznaci =  () => {
        if(platiSve) {
           setIznos(0);
           setRataIds([]);
        } else {
            const neplacene = rata.rata.filter(rata => !rata.placeno)
            const rate = neplacene.map(rata => rata.rataId)
            const iznosZaPlacanje = neplacene.reduce((suma, rataZaPlacanje) => suma + rataZaPlacanje.iznos, 0);

            setRataIds(rate);
            setIznos(iznosZaPlacanje);
        }

        setIsOznacene(false);

        setPlatiSve(!platiSve);

        const noveOznaceneRate = rata.rata.map((_, index) => !platiSve);
        setOznaceneRate(noveOznaceneRate);
    }


    const oznaciPojedinacno = (index) => {
        const noveOznaceneRate = [...oznaceneRata];
        noveOznaceneRate[index] = !noveOznaceneRate[index];
        setOznaceneRate(noveOznaceneRate);

        const rataId = rata.rata[index].rataId;
        const iznosRate = rata.rata[index].iznos

        setIznos(iznosRate + iznos);

        if (noveOznaceneRate[index]) {
            setRataIds([...rataIds, rataId])
        } else {
            setRataIds(rataIds.filter(id => id !== rataId));
            setIznos(iznos - iznosRate);
        }

        setIsOznacene(false);
    }


    const uzmiPodatke = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        })
    }

    const plati = async () => {
        setResponse(null);
        await RataService.plati({
            rataIds: rataIds,
            brojKartice: formData.brojKartice,
            datumIsteka: formData.datumIsteka,
            cvv: formData.cvv
        })
        .then(res => {
            if(res.success) {
                setResponse(res.podaci);
                RataService.getRata()
                    .then(res => {
                        setRata(res);
                    })
                close();
                setIsOdgovor(true);
                setIznos(0);
            } else {
                close()
                setIsOdgovor(true);
                setResponse(res.podaci);
            }
        })
    }

    const checkPlacanje = () => {
        if(rataIds.length === 0) {
            setIsOznacene(true);
            return;
        }

        setPlacanje(true);
    }

    const close = () => {
        setPlacanje(false)
        setRataIds([]);
    }

     return (

        <>
            <Header title={"Školarine i uplate"} />
            <Menu />
            <Container className={styles.container}>
                <Alert show={isOznacene} variant="danger">Molimo vas označite rate koje želite da platite</Alert>
                <Table>
                    <tbody>
                        <tr>
                            <th>Ukupno za plaćanje:</th>
                            <td>{iznos.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + " " + "RSD"}</td>
                            <th>Ukupno dugovanje:</th>
                            <td>{rata?.ukupnoDugovanje.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + " " + "RSD"}</td>
                            <th>Ukupno dugovanje na današnji dan:</th>
                            <td>{rata?.dugovanjeNaDanasnjiDan.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + " " + "RSD"}</td>
                            <th>Ukupno uplaćeno školarine:</th>
                            <td>{rata?.ukupnoUplaceno.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + " " + "RSD"}</td>
                        </tr>
                    </tbody>
                </Table>
                <Table>
                    <thead>
                        <tr>
                            <th><Form.Check onChange={() => oznaci()} label="Plati sve"/></th>
                            <th>Školska godina</th>
                            <th>Iznos</th>
                            <th>Rok plaćanja</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        rata?.rata?.map((r, index) => (
                        <tr>

                            <td>
                                <Form.Check value={r.rataId} onChange={() => oznaciPojedinacno(index)} checked={oznaceneRata[index] && !r.placeno} disabled={r.placeno}></Form.Check>
                            </td>

                            <td>{r.skolskaGodina}</td>
                            <td>{r.iznos.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + " " + "RSD"}</td>
                            <td>
                                {r.rokPlacanja.substring(8, 10) + "." + r.rokPlacanja.substring(5, 7) + "." + r.rokPlacanja.substring(0, 4) + "."}
                            </td>
                            {
                                r.placeno ? (
                                    <td style={{color: "green"}} >Plaćeno</td>
                                ):
                                    <td style={{color: "red"}}>Nije plaćeno</td>
                            }
                        </tr>
                        ))}
                    </tbody>
                </Table>
                <Button onClick={() => checkPlacanje()} variant="primary">Plati</Button>

                <Modal
                    size="lg"
                    aria-labelledby="contained-modal-title-vcenter"
                    centered
                    show={placanje}
                >
                    <Modal.Header onClick={() => close()} closeButton>
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

                            <Button onClick={e => plati()} variant="primary">Plati školarinu</Button>
                            <Button className={styles.dugmeOtkazi} onClick={() => close()} variant="danger">Otkaži</Button>
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