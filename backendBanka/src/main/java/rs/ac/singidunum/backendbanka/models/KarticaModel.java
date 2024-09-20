package rs.ac.singidunum.backendbanka.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "kartica")
@Data
public class KarticaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int karticaId;

    @Column(name = "broj_kartice")
    private String brojKartice;

    @Column(name = "izdavac")
    private String izdavac;

    @Column(name = "datum_isteka")
    private String datumIsteka;

    @Column(name = "cvv")
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "racun_id")
    private RacunModel racun;
}
