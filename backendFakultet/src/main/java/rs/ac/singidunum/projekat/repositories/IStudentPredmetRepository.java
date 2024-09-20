package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.singidunum.projekat.models.StudentPredmet;

import java.util.List;

public interface IStudentPredmetRepository extends JpaRepository<StudentPredmet, Integer> {
   StudentPredmet findByStudentStudentIdAndPredmetPredmetId(int studentId, int predmetId);

    List<StudentPredmet> findByStudentStudentIdAndIsAktivan(int studentId, boolean isAktivan);

    List<StudentPredmet> findByPredmetPredmetIdAndPredmetProfesorProfesorIdAndIsAktivan(int predmetId, int profesorId, boolean isAktivan);


}
