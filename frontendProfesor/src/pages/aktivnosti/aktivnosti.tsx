import styles from "./aktivnosti.module.css"
import Header from "../../Header/Header";
import Menu from "../../Menu/Menu";
import {Alert, Container, Form, Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import { useRouter } from 'next/router';
import PredmetService from "../../services/PredmetService";
import Button from "react-bootstrap/Button";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";

export default function Aktivnosti() {
    const router = useRouter();
    const {predmetId} = router.query;
    const [aktivnosti, setAktivnosti] = useState(null);
    const [formData, setFormData] = useState<any>([])
    const [isUspesno, setIsUspesno] = useState(false);
    const [isNeuspesno, setIsNeuspesno] = useState(false);
    const [sveAktivnosti, setSveAktivnosti] = useState(null);
    const {data: session} = useSession();

    useAuth();

    useEffect(() => {
        PredmetService.sendPredmetId({
            predmetId: Number(predmetId)
        })
            .then(res => {
                if(res.success) {
                    setAktivnosti(res.podaci);
                    setSveAktivnosti(res.podaci);
                }
            })

    }, [predmetId, session]);

    const uzmiPodatke =  (e, index) => {
       setIsUspesno(false);
       setIsNeuspesno(false);

       const {name, value} = e.target;

       const newFormData = [...formData];

       if(newFormData[index]) {

           if(value === "") {
               newFormData[index][name] = null;
           } else {
               newFormData[index][name] = Number(value)
           }

       } else {
           newFormData[index] = {studentPredmetId: aktivnosti[index].studentPredmetId, [name]: Number(value)}

       }

       setFormData(newFormData);
    }

    const sendAktivnost = () => {
       const podaciZaSlanje = formData.filter(podatak => podatak !== undefined);

       PredmetService.addAktivnost({
           aktivnostPodaci: podaciZaSlanje
        })
            .then(res => {
                if(res.success) {
                  PredmetService.sendPredmetId({
                        predmetId: Number(predmetId)
                    })
                    .then(res => {
                        if(res.success) {
                            setAktivnosti(res.podaci);
                        }
                    })

                    setIsUspesno(true);
                } else {
                    setIsNeuspesno(true);
                }
            })
    }

    const pretrazi = (e) => {
        const pojamZaPretragu = e.target.value;

        if (!pojamZaPretragu) {
            setAktivnosti(sveAktivnosti);
            return;
        }

        if (aktivnosti.length > 0) {
            const noviNiz = aktivnosti.filter(p => {
                const imePrezimeBrojIndeksa = `${p.student.ime.toLowerCase()} ${p.student.prezime.toLowerCase()} ${p.student.brIndeksa}`;
                const reciZaPretragu = pojamZaPretragu.split(" ");

                return reciZaPretragu.every(rec => {
                    const regex = new RegExp(`\\b${rec}`, 'i');
                    return regex.test(imePrezimeBrojIndeksa);
                });
            });

            setAktivnosti(noviNiz);
        } else if (aktivnosti.length === 0) {
            const noviNiz = sveAktivnosti.filter(p => {
                const imePrezimeBrojIndeksa = `${p.student.ime.toLowerCase()} ${p.student.prezime.toLowerCase()} ${p.student.brIndeksa}`;
                const reciZaPretragu = pojamZaPretragu.split(" ");

                return reciZaPretragu.every(rec => {
                    const regex = new RegExp(`\\b${rec}`, 'i');
                    return regex.test(imePrezimeBrojIndeksa);
                });
            });

            setAktivnosti(noviNiz);
        }
    }

    return (
        <>
            <Header title="Unos aktivnosti"/>
            <Menu />
            <Container className={styles.container}>
                <Form.Control style={{marginLeft: 'auto', marginRight: 'auto', width: '50%'}} onChange={(e) => pretrazi(e)} type="text" placeholder="Unesite pojam za pretragu" />
                <Alert variant="success" show={isUspesno}>Uspešno ste izvršili upis aktivnosti</Alert>
                <Alert variant="danger" show={isNeuspesno}>Došlo je do greške prilikom upisa aktivnosti, upis aktivnosti nije uspešan</Alert>
                <Table className={styles.tabela}>
                    <thead>
                        <tr>
                            <th>Ime</th>
                            <th>Prezime</th>
                            <th>Broj indeksa</th>
                            <th>Aktivnost</th>
                            <th>Prvi kolokvijum</th>
                            <th>Drugi kolokvijum</th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        aktivnosti?.map((aktivnost, index) => (
                        <tr>
                            <td>{aktivnost?.student.ime}</td>
                            <td>{aktivnost?.student.prezime}</td>
                            <td>{aktivnost?.student.brIndeksa}</td>
                            <td>
                                <Form.Control onChange={e => uzmiPodatke(e, index)} name="aktivnost" size="sm" defaultValue={aktivnost?.aktivnost} type="text" placeholder="Unesite aktivnost" />
                            </td>
                            <td>
                                <Form.Control onChange={e => uzmiPodatke(e, index)} name="prviKolokvijum" size="sm" defaultValue={aktivnost?.prviKolokvijum} type="text" placeholder="Unesite prvi kolokvijum" />
                            </td>
                            <td>
                                <Form.Control onChange={e => uzmiPodatke(e, index)} name="drugiKolokvijum" size="sm" defaultValue={aktivnost?.drugiKolokvijum} type="text" placeholder="Unesite drugi kolokvijum" />
                            </td>
                        </tr>
                        ))}
                    </tbody>
                </Table>
                <Button onClick={() => sendAktivnost()} variant="primary">Unesi aktivnosti</Button>
            </Container>
        </>
    )
}