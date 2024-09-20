import 'bootstrap/dist/css/bootstrap.min.css';
import ListGroup from 'react-bootstrap/ListGroup';
import Link from "next/link";
import styles from "./menu.module.css"
import {useEffect, useState} from "react";
import {useRouter} from "next/router";
import useAuth from "../hooks/useAuth";
import {signOut, useSession} from "next-auth/react";
import axios from "axios";
import StudentService from "../services/StudentService";

export default function Menu() {
    const [ispitSubMenu, setIspitSubMenu] = useState(false);
    const router = useRouter();
    const {data: session} = useSession();

    const logout = async () => {
        await StudentService.logout()
            .then(async res => {
                if(res) {
                    await signOut()
                }
            })
    }

    return (
        <div className={styles.menu}>
            <ListGroup className="flex-column" activeKey="/home"
                 onSelect={(selectedKey) => alert(`selected ${selectedKey}`)}>
                <ListGroup.Item>
                    <Link className={styles.podvlacenje} href={"../obavestenja"}>Početna</Link>
                </ListGroup.Item>
            </ListGroup>
            <ListGroup>
                <ListGroup.Item>
                    <Link className={styles.podvlacenje} href={"../obavestenja/staraObavestenja"}>Stara obaveštenja</Link>
                </ListGroup.Item>
            </ListGroup>
            <ListGroup>
                <ListGroup.Item>
                    <Link className={styles.podvlacenje} href={"../rata/skolarineIUplate"}>Školarina i uplate</Link>
                </ListGroup.Item>
            </ListGroup>
            <ListGroup>
                <ListGroup.Item>
                    <Link className={styles.podvlacenje} href={"../ispit/prijavaIspita"}>Prijava ispita</Link>
                </ListGroup.Item>
            </ListGroup>
            <ListGroup>
                <ListGroup.Item>
                   <Link onClick={() => setIspitSubMenu(!ispitSubMenu)} className={styles.podvlacenje} href={""}>Ispiti</Link>
                </ListGroup.Item>

                {ispitSubMenu ? (
                    <ListGroup variant="flush">
                        <ListGroup.Item>
                            <Link className={styles.podvlacenje} href={"../ispit/uspesnoPolozeni"}>Uspešno položeni</Link>
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <Link className={styles.podvlacenje} href={"../ispit/neuspesnaPolaganja"}>Neuspešna polaganja</Link>
                        </ListGroup.Item>
                    </ListGroup>
                ): null}
            </ListGroup>

            <ListGroup>
                <ListGroup.Item>
                    <Link className={styles.podvlacenje} href={"../aktivnost/aktivnosti"}>Aktivnosti</Link>
                </ListGroup.Item>

            <ListGroup>
                <ListGroup.Item>
                    <Link className={styles.podvlacenje} href={"../upisi"}>Upisi</Link>
                </ListGroup.Item>
            </ListGroup>

            </ListGroup>
            <ListGroup>
                <ListGroup.Item>
                    <Link className={styles.podvlacenje} href={"../student"}>Moji podaci</Link>
                </ListGroup.Item>
            </ListGroup>
            <ListGroup>
                <ListGroup.Item>
                    <Link onClick={logout} className={styles.podvlacenje} href={"#"}>Odjava</Link>
                </ListGroup.Item>
            </ListGroup>
    </div>
    )
}