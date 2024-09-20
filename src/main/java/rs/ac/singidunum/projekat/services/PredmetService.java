package rs.ac.singidunum.projekat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.dtos.AktivnostPodaciDTO;
import rs.ac.singidunum.projekat.models.PredmetModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.StudentPredmet;
import rs.ac.singidunum.projekat.repositories.IPredmetRepository;
import rs.ac.singidunum.projekat.repositories.IStudentPredmetRepository;
import rs.ac.singidunum.projekat.repositories.IStudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredmetService implements IPredmetService {

    @Autowired
    private IPredmetRepository predmetRepository;

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IStudentPredmetRepository studentPredmetRepository;

    @Override
    public List<StudentPredmet> getAllForStudent(int studentId) {
        List<StudentPredmet> predmeti = studentPredmetRepository.findByStudentStudentIdAndIsAktivan(studentId, true);

        if(predmeti.isEmpty()) {
            return null;
        }

        return predmeti;
    }

    @Override
    public StudentPredmet getPredmetForStudent(int studentId, int predmetId) {
        StudentPredmet studentPredmet = studentPredmetRepository.findByStudentStudentIdAndPredmetPredmetId(studentId, predmetId);

        if (studentPredmet == null) {
            return null;
        }

        if(studentPredmet.getStudent().getStudentId() != studentId) {
            return null;
        }

        return studentPredmet;
    }

    @Override
    public List<PredmetModel> getPredmetForProfesor(int profesorId) {
        List<PredmetModel> predmet = predmetRepository.findByProfesorProfesorId(profesorId);

        if (predmet.isEmpty()) {
            return null;
        }

        return predmet;
    }

    @Override
    public List<StudentPredmet> getUpisaniForProfesor(int predmetId, int profesorId) {
        List<StudentPredmet> upisaniStudenti = studentPredmetRepository.findByPredmetPredmetIdAndPredmetProfesorProfesorIdAndIsAktivan(predmetId, profesorId, true);

        if(upisaniStudenti.isEmpty()) {
            return null;
        }

        return upisaniStudenti;
    }

    @Override
    public StudentPredmet addAktivnostIspit(StudentPredmet sp, Integer aktivnost, Integer prviKolokvijum, Integer drugiKolokvijum) {
        StudentPredmet studentPredmet = sp;

        if (studentPredmet == null) {
            return null;
        }

        if(aktivnost != null) {
            studentPredmet.setAktivnost(aktivnost);
        }

        if(prviKolokvijum != null) {
            studentPredmet.setPrviKolokvijum(prviKolokvijum);
        }

        if(drugiKolokvijum != null) {
            studentPredmet.setDrugiKolokvijum(drugiKolokvijum);
        }

        return studentPredmetRepository.save(studentPredmet);
    }


    private StudentPredmet addAktivnost(int studentPredmetId, int profesorId, Integer aktivnost, Integer prviKolokvijum, Integer drugiKolokvijum) {
        StudentPredmet studentPredmet = studentPredmetRepository.findById(studentPredmetId).orElse(null);

        if(studentPredmet == null) {
            return null;
        }

        if(studentPredmet.getPredmet().getProfesor().getProfesorId() != profesorId) {
            return null;
        }

        if(aktivnost != null) {
           studentPredmet.setAktivnost(aktivnost);
        }

        if(prviKolokvijum != null) {
            studentPredmet.setPrviKolokvijum(prviKolokvijum);
        }

        if(drugiKolokvijum != null) {
            studentPredmet.setDrugiKolokvijum(drugiKolokvijum);
        }

        return studentPredmetRepository.save(studentPredmet);
    }

    @Override
    public boolean aktivnostUnos(List<AktivnostPodaciDTO> aktivnost, int profesorId) {
        if(aktivnost.isEmpty()) {
            return false;
        }

        for(AktivnostPodaciDTO a: aktivnost) {
            StudentPredmet studentPredmet = addAktivnost(a.getStudentPredmetId(), profesorId, a.getAktivnost(), a.getPrviKolokvijum(), a.getDrugiKolokvijum());

            if(studentPredmet == null) {
                return false;
            }
        }
        return true;
    }
}