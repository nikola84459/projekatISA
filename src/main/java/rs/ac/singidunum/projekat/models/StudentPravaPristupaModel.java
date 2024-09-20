package rs.ac.singidunum.projekat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student_prava_pristupa")
@Data
public class StudentPravaPristupaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentPravaPristupaId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentModel student;

    @ManyToOne
    @JoinColumn(name = "prava_pristupa_id")
    private PravaPristupaModel pravaPristupa;
}
