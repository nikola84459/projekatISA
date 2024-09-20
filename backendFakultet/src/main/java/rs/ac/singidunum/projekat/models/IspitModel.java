package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ispit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IspitModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ispitId;

    @Column(name = "ocena")
    private Integer ocena;

    @Column(name = "ispit_bodovi")
    private Integer bodovi;

    @Column(name = "bodovi_ukupno")
    private Integer bodoviUkupno;

    @Column(name = "is_izasao")
    private boolean isIzasao;

    @OneToOne
    @JoinColumn(name = "ispit_prijava_id")
    @JsonIgnore
    private IspitPrijavaModel ispitPrijava;

}