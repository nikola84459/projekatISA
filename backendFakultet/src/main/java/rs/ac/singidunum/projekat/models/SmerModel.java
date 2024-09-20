package rs.ac.singidunum.projekat.models;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "smer")
@Data
public class SmerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int smerId;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "akronim")
    private String akronim;

    @Column(name = "cena_skolarine")
    private int cenaSkolarine;

}
