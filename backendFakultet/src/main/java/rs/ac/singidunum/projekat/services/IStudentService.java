package rs.ac.singidunum.projekat.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.singidunum.projekat.models.PredmetModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.UpisModel;
import rs.ac.singidunum.projekat.response.PlacanjeResponse;

import java.util.List;

public interface IStudentService {
    List<StudentModel> getAll();

    StudentModel getById(int studentId);

    List<UpisModel> getUpis(int studentId);

    PlacanjeResponse updateStanjeNaRacunu(String brojKartice, String datumIsteka, String cvv, int studentId, double iznos);

    StudentModel getByBrIndeksa(String brIndeksa);

    boolean promenaSifre(PasswordEncoder passwordEncoder, String staraSifra, String sifra, String ponovoNovaSifra);

    int getPrijavljen();
}
