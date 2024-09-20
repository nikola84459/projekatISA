package rs.ac.singidunum.projekat.models;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "smer_predmet")
@Data
public class SmerPredmet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int smerPredmetId;

    @ManyToOne
    @JoinColumn(name = "smer_id")
    private SmerModel smer;

    @ManyToOne
    @JoinColumn(name = "predmet_id")
    private PredmetModel predmet;
}
