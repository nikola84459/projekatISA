"use client"
import Header from "../../Header/Header";
import Menu from "../../menu/Menu";
import {Alert, Container, Form} from "react-bootstrap";
import styles from "./student.module.css";
import React, {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import StudentService from "../../services/StudentService";
import {useSession} from "next-auth/react";
import useAuth from "../../hooks/useAuth";

export default function PromenaSifre() {
    const [formData, setFormData] = useState({
        staraSifra: "",
        novaSifra: "",
        ponovoNovaSifra: ""
    })
    const [isUspesno, setIsUspesno] = useState(false);
    const [isGreska, setIsGreska] = useState(false);
    const [poruka, setPoruka] = useState(null);
    const {data: session} = useSession();
    useAuth();

    useEffect(() => {

    }, [session]);

    const uzmiPodatke = (e) => {
        setIsGreska(false);
        setIsUspesno(false);

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        })
    }

    const promenaSifre = () => {
        if(formData.novaSifra !== formData.ponovoNovaSifra) {
            setIsGreska(true);
            setPoruka("Šifre se ne poklapaju.")
            return;
        }

        StudentService.promenaSifre({
            staraSifra: formData.staraSifra,
            sifra: formData.novaSifra,
            ponovoNovaSifra: formData.ponovoNovaSifra
        })
        .then(res => {
            if(res.success) {
                setIsUspesno(true);
            } else {
                setIsGreska(true);
                setPoruka(res.message);
            }
        })
    }

    return (
        <>
            <Header title={"Promena šifre"} />
            <Menu />
            <Container className={styles.container}>
                <Alert show={isUspesno} variant="success">Uspešno ste izvršili promenu šifre</Alert>
                <Alert show={isGreska} variant="danger">{poruka}</Alert>
                <Form>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>Stara šifra</Form.Label>
                        <Form.Control onChange={e => uzmiPodatke(e)} name="staraSifra" placeholder="Unesite staru šifru" type="password"/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>Nova šifra</Form.Label>
                        <Form.Control onChange={e => uzmiPodatke(e)} name="novaSifra" placeholder="Unesite novu šifru" type="password"/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>Ponovo unesite novu šifru</Form.Label>
                        <Form.Control onChange={e => uzmiPodatke(e)} name="ponovoNovaSifra" placeholder="Unesit ponovo novu šifru" type="password"/>
                    </Form.Group>
                </Form>
                <Button onClick={() => promenaSifre()} variant="primary">Promeni šifru</Button>
            </Container>
        </>
    )
}