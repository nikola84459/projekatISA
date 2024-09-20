import Header from "../Header/Header";
import {Alert, Container, Form} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import Button from "react-bootstrap/Button";
import React, {ReactNode, useEffect, useState} from "react";
import styles from "./student/student.module.css"
import {signIn, useSession} from 'next-auth/react';
import {useRouter} from "next/router";


export default function Prijava() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [poruka, setPoruka] = useState(null);
    const router = useRouter();
    const {data: session} = useSession();

    useEffect(() => {
        //@ts-ignore
        if(session && session.user.uloga === "Student") {
            router.push("/obavestenja");
        }
    }, [session]);

    const login = async () => {
        const result = await signIn("credentials", {
                username,
                password,
                redirect: false,
            });

        if(result.ok) {
            await router.push("/obavestenja");
        } else {
            setPoruka(result.error);
        }
    }

    return (

        <>
           <Header title="Prijava na sistem"/>
            <Container style={{width: 850}} className={styles.container}>
                <Alert show={poruka !== null} variant="danger">{poruka}</Alert>
                <Form>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>Korisničko ime</Form.Label>
                        <Form.Control onChange={e => setUsername(e.target.value)} placeholder="Korisničko ime" type="text"/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>Šifra</Form.Label>
                        <Form.Control  onChange={e => setPassword(e.target.value)} placeholder="Lozinka" type="password"/>
                    </Form.Group>
                </Form>
                <Button onClick={login} variant="primary">Prijavi se</Button>
            </Container>
        </>
    )
}

