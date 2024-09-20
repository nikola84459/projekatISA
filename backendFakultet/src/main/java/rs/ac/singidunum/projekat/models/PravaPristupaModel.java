package rs.ac.singidunum.projekat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prava_pristupa")
@Data
public class PravaPristupaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PravaPristupaId;

    @Column(name = "naziv")
    String naziv;
}
