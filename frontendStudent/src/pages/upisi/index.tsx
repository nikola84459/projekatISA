"use client"
import Header from "../../Header/Header";
import Menu from "../../menu/Menu";
import {Container} from "react-bootstrap";
import styles from "./upisi.module.css"
import Table from "react-bootstrap/Table";
import {UpisPodaci} from "../../types/Upis";
import {useEffect, useState} from "react";
import UpisService from "../../services/UpisService";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";

function getUpis(): UpisPodaci[] {
    const [upis, setUpis] = useState(null);
    const {data: session} = useSession();
    useAuth();

    useEffect(() => {
        const getUpisi = async () => {
            await UpisService.getUpis()
                .then(res => {
                    setUpis(res);
                })
        }

        getUpisi();
    }, [session]);

    return upis;
}
export default function Upisi() {
    const upisi = getUpis();

    return (
        <>
            <Header title={"Upisi"} />
            <Menu />
            <Container className={styles.container}>
                <Table>
                    <thead>
                        <tr>
                            <th>Smer</th>
                            <th>Akronim</th>
                            <th>Akademska godina</th>
                            <th>Godina studija</th>
                            <th>Najava ESPB</th>
                            <th>Ostvareno ESPB</th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        upisi?.map(upis => (
                        <tr>
                            <td>{upis?.smer?.naziv}</td>
                            <td>{upis?.smer?.akronim}</td>
                            <td>{upis?.akademskaGodina}</td>
                            <td>{upis?.godinaStudija}</td>
                            <td>{upis?.najavaEspb}</td>
                            <td>{upis?.ostvarenoEspb}</td>
                        </tr>
                        ))}
                    </tbody>
                </Table>
            </Container>
        </>
    )
}