package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ispitni_rok")
@Data
public class IspitniRokModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ispitniRokId;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "pocetak")
    private LocalDate pocetak;

    @Column(name = "kraj")
    private LocalDate kraj;

    @Column(name = "pocetak_prijave")
    private LocalDate pocetakPrijave;

    @Column(name = "cena_redovne_prijave")
    private int cenaRedovnePrijave;

    @Column(name = "cena_vanredne_prijave")
    private int cenaVanrednePrijave;

    @OneToMany(mappedBy = "ispitniRok")
    @JsonIgnore
    private List<IspitPrijavaModel> ispitPrijava;

}
