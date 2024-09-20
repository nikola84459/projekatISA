package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "rata")
@Data
public class RataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rataId;

    @Column(name = "skolska_godina")
    private String skolskaGodina;

    @Column(name = "iznos")
    private float iznos;

    @Column(name = "rok_placanja")
    private LocalDate rokPlacanja;

    @Column(name = "is_placeno")
    private boolean isPlaceno;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private StudentModel student;
}
