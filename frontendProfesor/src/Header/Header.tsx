import React from "react";
import styles from "./header.module.css"

export default function Header(props) {
    return (
        <div className={styles.header}>
            <h1>{props.title}</h1>
        </div>
    )
}