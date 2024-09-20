package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "predmet")
@Data
public class PredmetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int predmetId;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "akronim")
    private String akronim;

    @Column(name = "espb")
    private int espb;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private ProfesorModel profesor;
}