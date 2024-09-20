package rs.ac.singidunum.backendbanka.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "korisnik_pravno_lice")
@Data
public class KorisnikPravnoLiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int korisnikPravnoLiceId;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "pib")
    private String pib;

    @Column(name = "broj")
    private String broj;
}
