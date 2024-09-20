package rs.ac.singidunum.backendplacanje.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "banka")
@Data
public class BankaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bankaId;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "password_hash")
    private String password_hash;

    @Column(name = "broj")
    private String broj;

    @Column(name = "link")
    private String link;
}
