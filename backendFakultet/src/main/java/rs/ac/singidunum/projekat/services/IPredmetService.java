package rs.ac.singidunum.projekat.services;

import rs.ac.singidunum.projekat.dtos.AktivnostPodaciDTO;
import rs.ac.singidunum.projekat.models.PredmetModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.StudentPredmet;

import java.util.List;
import java.util.Objects;

public interface IPredmetService {
    List<StudentPredmet> getAllForStudent(int studentId);

    StudentPredmet getPredmetForStudent(int studentId, int predmetId);

    List<PredmetModel> getPredmetForProfesor(int profesorId);

    List<StudentPredmet> getUpisaniForProfesor(int predmetId, int profesorId);

    StudentPredmet addAktivnostIspit(StudentPredmet sp, Integer aktivnost, Integer prviKolokvijum, Integer drugiKolokvijum);

    boolean aktivnostUnos(List<AktivnostPodaciDTO> aktivnost, int profesorId);
}
