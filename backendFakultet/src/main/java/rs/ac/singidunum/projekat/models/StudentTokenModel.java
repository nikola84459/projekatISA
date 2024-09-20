package rs.ac.singidunum.projekat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentTokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentTokenId;

    @Column(name = "token")
    private String token;

    @Column(name = "is_nevazeci")
    private boolean isNevazeci;

    @Column(name = "is_istekao")
    private boolean isIstekao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentModel student;
}