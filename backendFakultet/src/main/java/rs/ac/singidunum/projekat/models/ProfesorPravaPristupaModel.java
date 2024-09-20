package rs.ac.singidunum.projekat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "profesor_prava_pristupa")
@Data
public class ProfesorPravaPristupaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profesorPravaPristupaId;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private ProfesorModel profesor;

    @ManyToOne
    @JoinColumn(name = "prava_pristupa_id")
    private PravaPristupaModel pravaPristupa;
}
