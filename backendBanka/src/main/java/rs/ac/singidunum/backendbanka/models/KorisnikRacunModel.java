package rs.ac.singidunum.backendbanka.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "korisnik_racun")
@Data
public class KorisnikRacunModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int korisnikRacunId;

    @ManyToOne
    @JoinColumn(name = "korisnik_id")
    private KorisnikModel korisnik;

    @ManyToOne
    @JoinColumn(name = "racun_id")
    private RacunModel racun;
}
