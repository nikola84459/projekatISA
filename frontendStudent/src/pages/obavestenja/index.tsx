"use client"
import {useSession} from "next-auth/react";
import Header from "../../Header/Header";
import {Card, Container} from "react-bootstrap";
import Menu from "../../menu/Menu";
import styles from "./obavestenja.module.css"
import {useEffect, useState} from "react";
import ObavestenjeService from "../../services/ObavestenjeService";
import useAuth from "../../hooks/useAuth";
import {useRefreshToken} from "../../hooks/useRefresh";
import {useRouter} from "next/router";


export default function Obavestenja() {
    const [obavestenja, setObavestenje] = useState(null);
    const router = useRouter();
    const { data: session } = useSession();

    useAuth()

    useEffect(() => {
        const getObavestenja = async (): Promise<void> => {
           await ObavestenjeService.getObavestenje()
                .then(async res => {
                    setObavestenje(res);
                })
            }

        getObavestenja();
    }, [session]);


    return (
        <>
           <Header title={"Obaveštenja"} />
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