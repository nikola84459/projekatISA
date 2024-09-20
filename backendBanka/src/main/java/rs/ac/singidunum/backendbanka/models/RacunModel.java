package rs.ac.singidunum.backendbanka.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "racun")
@Data
public class RacunModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int racunId;

    @Column(name = "br_racuna")
    private String brRacuna;

    @Column(name = "stanje")
    private double stanje;

    @Column(name = "datumOtvaranja")
    private LocalDate datumOtvaranja;

}
