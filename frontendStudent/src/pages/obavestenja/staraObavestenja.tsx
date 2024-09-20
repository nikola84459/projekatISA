"use client"
import Header from "../../Header/Header";
import Menu from "../../menu/Menu";
import {Card, Container} from "react-bootstrap";
import styles from "./obavestenja.module.css"
import {useEffect, useState} from "react";
import ObavestenjeService from "../../services/ObavestenjeService";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";

export default function StaraObavestenja() {
    const [obavestenja, setObavestenje] = useState(null);
    const {data: session} = useSession();
    useAuth();

    useEffect(() => {
        const getObavestenja = async () => {
            return await ObavestenjeService.getStaraObavestenja()
                .then(res => {
                    setObavestenje(res);
            })

        }

        getObavestenja();
    }, [session]);


    return (
        <>
            <Header title={"Stara obaveštenja"} />
            <Menu />
            <Container className={styles.container}>
                {
                    obavestenja?.map(obavestenje => (
                        <>
                        <Card className={styles.obavestenjaPrikaz}>
                            <Card.Body>
                                <Card.Title>{obavestenje.naslov}</Card.Title>
                                <Card.Header>
                                    {
                                        obavestenje.datumPostavljanja.substring(8, 10) + "." + obavestenje.datumPostavljanja.substring(5, 7) + "." + obavestenje.datumPostavljanja.substring(0, 4) + "." + " - " +
                                        obavestenje.datumIsteka.substring(8, 10) + "." + obavestenje.datumIsteka.substring(5, 7) + "." + obavestenje.datumIsteka.substring(0, 4) + "."

                                    }
                                </Card.Header>
                                <Card.Body>{obavestenje.tekst}</Card.Body>
                            </Card.Body>
                        </Card>
                        <br />
                        </>
                    ))}
            </Container>
        </>
    )
}