package rs.ac.singidunum.backendbanka.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "korisnik_pravno_lice_racun")
@Data
public class KorisnikPravnoLiceRacunModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int korisnikPravnoLiceRacunId;

    @ManyToOne
    @JoinColumn(name = "korisnik_pravno_lice_id")
    private KorisnikPravnoLiceModel korisnikPravnoLice;

    @ManyToOne
    @JoinColumn(name = "racun_id")
    private RacunModel racun;
}
