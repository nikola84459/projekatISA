import Header from "../../Header/Header";
import Menu from "../../Menu/Menu";
import {Container, Form, Table} from "react-bootstrap";
import styles from "./ispiti.module.css"
import {useEffect, useState} from "react";
import {useRouter} from "next/router";
import IspitService from "../../services/IspitService";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";


export default function ZakljuceniIspiti() {
    const [zakljuceni, setZakljuceni] = useState(null);
    const [sviZakljuceni, setSviZakljuceni] = useState(null);
    const {predmetId} = useRouter().query;
    const {ispitniRokId} = useRouter().query;
    const {data: session} = useSession();

    useAuth();

    useEffect(() => {
        IspitService.getZakljuceni({
            predmetId: Number(predmetId),
            ispitniRokId: Number(ispitniRokId)
        })
        .then(res => {
            if(res.success) {
                setZakljuceni(res.podaci)
                setSviZakljuceni(res.podaci)
            }
        })
    }, [predmetId, ispitniRokId, session]);

    const pretrazi = (e) => {
        const pojamZaPretragu = e.target.value;

        if(!pojamZaPretragu) {
            setZakljuceni(sviZakljuceni);
            return;
        }
        if(zakljuceni.length > 0) {
            const noviNiz = zakljuceni.filter(p => {
                const imePrezimeBrojIndeksa = `${p.studentPredmet.student.ime.toLowerCase()} ${p.studentPredmet.student.prezime.toLowerCase()} ${p.studentPredmet.student.brIndeksa}`;
                const reciZaPretragu = pojamZaPretragu.split(" ");

                return reciZaPretragu.every(rec => {
                    const regex = new RegExp(`\\b${rec}`, 'i');
                    return regex.test(imePrezimeBrojIndeksa);
                });
            });

            setZakljuceni(noviNiz);
        } else if (zakljuceni.length === 0) {
            const noviNiz = sviZakljuceni.filter(p => {
                const imePrezimeBrojIndeksa = `${p.studentPredmet.student.ime.toLowerCase()} ${p.studentPredmet.student.prezime.toLowerCase()} ${p.studentPredmet.student.brIndeksa}`;
                const reciZaPretragu = pojamZaPretragu.split(" ");

                return reciZaPretragu.every(rec => {
                    const regex = new RegExp(`\\b${rec}`, 'i');
                    return regex.test(imePrezimeBrojIndeksa);
                });
            });

            setZakljuceni(noviNiz);
        }
    }
    console.log(zakljuceni);
    return (
        <>
            <Header title="Zaključeni ispiti"/>
            <Menu />
            <Container className={styles.container}>
                <Form.Control style={{marginLeft: 'auto', marginRight: 'auto', width: '50%'}} onChange={(e) => pretrazi(e)} type="text" placeholder="Unesite pojam za pretragu" />
                <Table className={styles.tabela}>
                    <thead>
                        <tr>
                            <th>Ime</th>
                            <th>Prezime</th>
                            <th>Broj indeksa</th>
                            <th>Ispitni rok</th>
                            <th>Aktivnost</th>
                            <th>Prvi kolokvijum</th>
                            <th>Drugi kolokvijum</th>
                            <th>Ispit bodovi</th>
                            <th>Bodovi ukupno</th>
                            <th>Ocena</th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        zakljuceni?.map(zakljucen => (
                        <tr>
                            <td>{zakljucen?.studentPredmet.student.ime}</td>
                            <td>{zakljucen?.studentPredmet.student.prezime}</td>
                            <td>{zakljucen?.studentPredmet.student.brIndeksa}</td>
                            <td>{zakljucen?.ispitniRok.naziv}</td>
                            <td>{zakljucen?.studentPredmet.aktivnost}</td>
                            <td>{zakljucen?.studentPredmet.prviKolokvijum}</td>
                            <td>{zakljucen?.studentPredmet.drugiKolokvijum}</td>
                            <td>{zakljucen?.ispit.bodovi}</td>
                            <td>{zakljucen?.ispit.bodoviUkupno}</td>
                            <td>{zakljucen?.ispit.ocena}</td>
                        </tr>
                        ))}
                    </tbody>
                </Table>
            </Container>
        </>
    )
}