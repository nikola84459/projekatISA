package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "ispit_raspored")
@Data
public class IspitRaspored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ispitRasporedId;

    @ManyToOne
    @JoinColumn(name = "ispitni_rok_id")
    @JsonIgnore
    private IspitniRokModel ispitniRok;

    @ManyToOne
    @JoinColumn(name = "predmet_id")
    @JsonIgnore
    private PredmetModel predmet;

    @Column(name = "datum_i_vreme")
    private LocalDateTime datumIVreme;
}
