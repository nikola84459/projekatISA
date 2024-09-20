package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "upis")
@Data
public class UpisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int upisId;

    @ManyToOne
    @JoinColumn(name = "smer_id")
    private SmerModel smer;

    @Column(name = "akademska_godina")
    private String akademskaGodina;

    @Column(name = "godina_studija")
    private String godinaStudija;

    @Column(name = "najava_espb")
    private int najavaEspb;

    @Column(name = "is_aktivan")
    private boolean isAktivan;

    @Column(name = "ostvareno_espb")
    private int ostvarenoEspb;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private StudentModel student;

}
