package rs.ac.singidunum.projekat.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.singidunum.projekat.models.ProfesorModel;

public interface IProfesorService {
    ProfesorModel getProfesorById(int profesorId);

    ProfesorModel getProfesorByKime(String kime);

    int getPrijavljen();

    boolean promenaSifre(PasswordEncoder passwordEncoder, String staraSifra, String sifra, String ponovoNovaSifra);
}
