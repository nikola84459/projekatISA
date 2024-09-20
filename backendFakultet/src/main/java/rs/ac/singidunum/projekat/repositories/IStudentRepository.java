package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.StudentPredmet;

@Repository
public interface IStudentRepository extends JpaRepository<StudentModel, Integer> {
    StudentModel findByBrIndeksa(String brIndeksa);
}
