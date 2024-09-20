package rs.ac.singidunum.projekat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "racun")
@Data
public class RacunModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int racunId;

    @Column(name = "broj")
    private String broj;

    @ManyToOne
    @JoinColumn(name = "smer_id")
    private SmerModel smer;
}
