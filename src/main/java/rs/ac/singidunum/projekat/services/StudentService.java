package rs.ac.singidunum.projekat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.models.PredmetModel;
import rs.ac.singidunum.projekat.models.RacunModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.UpisModel;
import rs.ac.singidunum.projekat.repositories.IStudentRepository;
import rs.ac.singidunum.projekat.repositories.IUpisRepository;
import rs.ac.singidunum.projekat.response.PlacanjeResponse;

import java.util.List;


@Service
public class StudentService implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IPlacanjeService iPlacanjeService;

    @Autowired
    private IUpisRepository upisRepository;

    @Autowired
    private IRataService rataService;


    @Override
    public List<StudentModel> getAll() {
        return studentRepository.findAll();
    }

    public StudentModel getById(int studentId) {
        return studentRepository.findById(studentId)
                .orElse(null);
    }


    @Override
    public List<UpisModel> getUpis(int studentId) {
        StudentModel student = studentRepository.findById(studentId)
                .orElse(null);

        if(student == null) {
            return null;
        }

        List<UpisModel> upis = student.getUpis();

        if(upis.isEmpty()) {
            return null;
        }

        return upis;
    }

    @Override
    public PlacanjeResponse updateStanjeNaRacunu(String brojKartice, String datumIsteka, String cvv, int studentId, double iznos) {
        StudentModel student = studentRepository.findById(studentId).orElse(null);

        if(student == null) {
            return null;
        }

        UpisModel upis = upisRepository.findByStudentStudentIdAndIsAktivan(studentId, true);

        RacunModel racun = rataService.getRacun(upis.getSmer().getSmerId());

        if(racun == null) {
            return null;
        }

        String racunBr = racun.getBroj();

        PlacanjeResponse placanje = iPlacanjeService.bankaKonekcija(brojKartice, datumIsteka, cvv, iznos, racunBr);


        if(placanje.isUspesno()) {
            student.setStanjeNaRacunu(student.getStanjeNaRacunu() + iznos);
            studentRepository.save(student);
        }

        return placanje;
    }

    @Override
    public StudentModel getByBrIndeksa(String brIndeksa) {
        StudentModel student = studentRepository.findByBrIndeksa(brIndeksa);

        if(student == null) {
            return null;
        }

        return student;
    }

    @Override
    public boolean promenaSifre(PasswordEncoder passwordEncoder, String staraSifra,String sifra, String ponovoNovaSifra) {
        int studentId = getPrijavljen();

        StudentModel student = studentRepository.findById(studentId).orElse(null);

        if(student == null) {
            return false;
        }

        if(!passwordEncoder.matches(staraSifra, student.getPassword())) {
            throw new IllegalArgumentException("Uneta stara šifra nije ispravna");
        }

        if(!sifra.equals(ponovoNovaSifra)) {
            throw new IllegalArgumentException("Šifre se ne poklapaju.");
        }

        student.setPasswordHash(passwordEncoder.encode(sifra));
        studentRepository.save(student);

        return true;
    }

    @Override
    public int getPrijavljen() {
        int studentId = 0;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userDetails instanceof StudentModel) {
          studentId = ((StudentModel) userDetails).getStudentId();
        }

        return studentId;
    }
}