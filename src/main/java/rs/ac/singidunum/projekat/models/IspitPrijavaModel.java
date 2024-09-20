package rs.ac.singidunum.projekat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ispit_prijava")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IspitPrijavaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ispitPrijavaId;

    @ManyToOne
    @JoinColumn(name = "student_predmet_id")
    private StudentPredmet studentPredmet;

    @ManyToOne
    @JoinColumn(name = "ispitni_rok_id")
    private IspitniRokModel ispitniRok;

    @Column(name = "is_polozen")
    private boolean isPolozen;

    @OneToOne(mappedBy = "ispitPrijava", fetch = FetchType.LAZY)
    private IspitModel ispit;

    @Column(name = "is_zakljucen")
    private boolean isZakljucen;
}