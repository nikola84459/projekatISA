package rs.ac.singidunum.projekat.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "obavestenje")
@Data
public class ObavestenjeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int obavestenjeId;

    @Column(name = "naslov")
    private String naslov;

    @Column(name = "tekst")
    private String tekst;

    @Column(name = "datum_postavljanja")
    private LocalDate datumPostavljanja;

    @Column(name = "datum_isteka")
    private LocalDate datumIsteka;
}
