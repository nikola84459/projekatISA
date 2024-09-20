package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "student_predmet")
@Data
public class StudentPredmet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentPredmetId;

    @Column(name = "aktivnost")
    private Integer aktivnost;

    @Column(name = "prvi_kolokvijum")
    private Integer prviKolokvijum;

    @Column(name = "drugi_kolokvijum")
    private Integer drugiKolokvijum;

    @Column(name = "is_aktivan")
    private boolean isAktivan;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentModel student;

    @ManyToOne
    @JoinColumn(name = "predmet_id")
    private PredmetModel predmet;
}

