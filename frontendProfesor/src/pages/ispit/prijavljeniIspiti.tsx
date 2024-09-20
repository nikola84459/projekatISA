import styles from "./ispiti.module.css";
import Header from "../../Header/Header";
import Menu from "../../Menu/Menu";
import {Alert, Container, Form, Table} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {useEffect, useState} from "react";
import {useRouter} from "next/router";
import IspitService from "../../services/IspitService";
import {auto} from "@popperjs/core";
import useAuth from "../../hooks/useAuth";
import {useSession} from "next-auth/react";

export default function PrijavljeniIspiti() {
    const [isUspesno, setIsUspesno] = useState(false)
    const [isUspesnoZakljucen, setIsUspesnoZakljucen] = useState(false)
    const [isNeuspesno, setIsNeuspesno] = useState(false);
    const [prijavljeni, setPrijavljeni] = useState(null);
    const [formData, setFormData] = useState([]);
    const [oznaciSve, setOznaciSve] = useState(false);
    const [prijavaIds, setPrijavaIds] = useState<number[]>([]);
    const [oznacenCheckbox, setOznacenCheckBox] = useState([]);
    const [sviPrijavljeni, setSviPrijavljeni] = useState([]);
    const [unosPretrage, setUnosPretrage] = useState("");
    const [poruka, setPoruka] = useState(null);
    const {data: session} = useSession();
    const {predmetId} = useRouter().query;
    const {ispitniRokId} = useRouter().query;

    useAuth();

    useEffect(() => {
        IspitService.getPrijava({
            predmetId: Number(predmetId),
            ispitniRokId: Number(ispitniRokId)
        })
            .then(res => {
                if(res.success) {
                    setPrijavljeni(res.podaci);
                    setSviPrijavljeni(res.podaci);
                }
            })
    }, [predmetId, ispitniRokId, session]);

    const uzmiPodatke =  (e, index) => {
        setIsUspesno(false);
        setIsNeuspesno(false);

        const {name, value} = e.target;

        const newFormData = [...formData];

        if (newFormData[index]) {
            if (value === "") {
                newFormData[index][name] = 0;
            } else {
                newFormData[index][name] = Number(value)
            }

        } else {
            newFormData[index] = {ispitPrijavaId: prijavljeni[index].ispitPrijavaId, [name]: Number(value)}
        }

        setFormData(newFormData)
    }

    const sendIspitData = () => {
        const podaciZaSlanje = formData.filter(podatak => podatak !== undefined);

        if(formData.length === 0) {
            setIsNeuspesno(true);
            return;
        }

        IspitService.addIspit({
            ispitPodaci: podaciZaSlanje
        })
            .then(res => {
                if(res.success) {
                    IspitService.getPrijava({
                        predmetId: Number(predmetId),
                        ispitniRokId: Number(ispitniRokId)
                    })
                        .then(res => {
                            if(res.success) {
                                setPrijavljeni(res.podaci);
                            }
                        })

                    setIsUspesno(true);
                } else {
                    setPoruka(res.message);
                    setIsNeuspesno(true);
                }
            })
    }

    const oznaci =  () => {
        const noviPrijavaIds = [];

        if (!oznaciSve) {
            const prijave = prijavljeni.map(prijava => prijava.ispitPrijavaId);
            noviPrijavaIds.push(...prijave);
        }

        setOznaciSve(!oznaciSve);
        setPrijavaIds(noviPrijavaIds);
    }

    const oznaciPojedinacno = (index) => {
        const noviOznaceniCheckboxovi = [...oznacenCheckbox];
        noviOznaceniCheckboxovi[index] = !noviOznaceniCheckboxovi[index];

        if (!noviOznaceniCheckboxovi[index]) {
            const idZaUklanjanje = prijavljeni[index].ispitPrijavaId;
            setPrijavaIds(prevIds => prevIds.filter(id => id !== idZaUklanjanje));
        } else {
            setPrijavaIds([...prijavaIds, prijavljeni[index].ispitPrijavaId]);
        }

        setOznacenCheckBox(noviOznaceniCheckboxovi);
    }

    const zakljuciPrijavu = () => {
        IspitService.setIsZakljucen({
            ispIds: prijavaIds
        })
        .then(res => {
            if(res.success) {
                IspitService.getPrijava({
                    predmetId: Number(predmetId),
                    ispitniRokId: Number(ispitniRokId)
                })
                    .then(res => {
                        if(res.success) {
                            setPrijavljeni(res.podaci);
                        }
                    })
                setIsUspesnoZakljucen(true);
            }
        })
    }

    const pretrazi = (e) => {
        const pojamZaPretragu = e.target.value;
        setUnosPretrage(pojamZaPretragu);

        if(!pojamZaPretragu) {
            setPrijavljeni(sviPrijavljeni);
            return;
        }

        if(prijavljeni.length > 0) {
            const noviNiz = prijavljeni.filter(p => {
                const imePrezimeBrojIndeksa = `${p.studentPredmet.student.ime.toLowerCase()} ${p.studentPredmet.student.prezime.toLowerCase()} ${p.studentPredmet.student.brIndeksa}`;
                const reciZaPretragu = pojamZaPretragu.split(" ");

                return reciZaPretragu.every(rec => {
                    const regex = new RegExp(`\\b${rec}`, 'i');
                    return regex.test(imePrezimeBrojIndeksa)
                });
            });

            setPrijavljeni(noviNiz);
        } else if (prijavljeni.length === 0) {
            const noviNiz = sviPrijavljeni.filter(p => {
                const imePrezimeBrojIndeksa = `${p.studentPredmet.student.ime.toLowerCase()} ${p.studentPredmet.student.prezime.toLowerCase()} ${p.studentPredmet.student.brIndeksa}`;
                const reciZaPretragu = pojamZaPretragu.split(" ");

                return reciZaPretragu.every(rec => {
                    const regex = new RegExp(`\\b${rec}`, 'i');
                    return regex.test(imePrezimeBrojIndeksa)
                });
            });

            setPrijavljeni(noviNiz);
        }
    }
    console.log(prijavljeni);
    return (
        <>
            <Header title="Pregled i unos prijavljenih studenata" />
            <Menu />
            <Container style={{maxWidth: '77%'}} className={styles.container}>
                <Form.Control style={{marginLeft: 'auto', marginRight: 'auto', width: '50%'}} onChange={(e) => pretrazi(e)} type="text" placeholder="Unesite pojam za pretragu" />
                <Alert show={isUspesno} variant="success" >Uspešno ste izvršili upis podataka za prijavljene ispite.</Alert>
                <Alert show={isUspesnoZakljucen} variant="success" >Uspešno ste zaključili prijave.</Alert>
                <Alert show={isNeuspesno} variant="danger">{poruka === null ? "Došlo je do greške prilikom upisa podataka za ispite, upis ispita nije uspešan." : poruka}</Alert>
                <Table className={styles.tabela}>
                    <thead>
                        <tr>
                            <th><Form.Check onClick={() => oznaci()} label="Zaključi"/></th>
                            <th>Ime</th>
                            <th>Prezime</th>
                            <th>Broj indeksa</th>
                            <th>Ispitni rok</th>
                            <th>Bodovi ukupno</th>
                            <th>Aktivnost</th>
                            <th>Prvi kolokvijum</th>
                            <th>Drugi kolokvijum</th>
                            <th>Ispit bodovi</th>
                            <th>Ocena</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            prijavljeni?.map((prijavljen, index) => (
                                <tr>
                                    <td><Form.Check onClick={() => oznaciPojedinacno(index)} checked={oznacenCheckbox[index] || oznaciSve}/></td>
                                    <td>{prijavljen?.studentPredmet.student.ime}</td>
                                    <td>{prijavljen?.studentPredmet.student.prezime}</td>
                                    <td>{prijavljen?.studentPredmet.student.brIndeksa}</td>
                                    <td>{prijavljen?.ispitniRok.naziv}</td>
                                    <td>{prijavljen?.ispit?.bodoviUkupno}</td>
                                    <td>
                                        <Form.Control name="aktivnost" onChange={(e) => uzmiPodatke(e, index)} defaultValue={prijavljen?.studentPredmet.aktivnost} type="text" placeholder="Unesite aktivnost" />
                                    </td>
                                    <td>
                                        <Form.Control name="prviKolokvijum" onChange={(e) => uzmiPodatke(e, index)} defaultValue={prijavljen?.studentPredmet.prviKolokvijum}  type="text" placeholder="Unesite prvi kolokvijum" />
                                    </td>
                                    <td>
                                        <Form.Control name="drugiKolokvijum" onChange={(e) => uzmiPodatke(e, index)} defaultValue={prijavljen?.studentPredmet.drugiKolokvijum} type="text" placeholder="Unesite drugi kolokvijum" />
                                    </td>
                                    <td>
                                        <Form.Control name="ispitBodovi" onChange={(e) => uzmiPodatke(e, index)} defaultValue={prijavljen?.ispit?.bodovi} type="text" placeholder="Unesite ispit" />
                                    </td>
                                    <td>
                                        <Form.Select name="ispitOcena" defaultValue={prijavljen?.ispit?.izasao || prijavljen?.ispit == null ? prijavljen?.ispit?.ocena : "0"} onChange={(e) => uzmiPodatke(e, index)}>
                                            <option>Ocena</option>
                                            <option value="0">NI (Nije izašao)</option>
                                            <option value="5">5 (pet)</option>
                                            <option value="6">6 (šest)</option>
                                            <option value="7">7 (sedam)</option>
                                            <option value="8">8 (osam)</option>
                                            <option value="9">9 (devet)</option>
                                            <option value="10">10 (deset)</option>
                                        </Form.Select>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                    <Button onClick={() => sendIspitData()} variant="primary">Unesi ispit</Button>
                    <Button onClick={() => zakljuciPrijavu()} style={{marginLeft: "0.5rem"}} variant="primary">Zaključi prijave</Button>
            </Container>
        </>
    )
}