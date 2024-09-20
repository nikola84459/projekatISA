package rs.ac.singidunum.backendbanka.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "korisnik")
public class KorisnikModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int korisnikId;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "email")
    @Email
    private String email;
}
