package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.singidunum.projekat.models.IspitModel;

import java.util.List;

public interface IIspitRepository extends JpaRepository<IspitModel, Integer> {
    @Query("SELECT i FROM IspitModel i WHERE i.ispitPrijava.studentPredmet.student.studentId = :studentId")
    List<IspitModel> findByStudentId(int studentId);

    IspitModel findByIspitPrijavaIspitPrijavaId(int ispitPrijavaId);
}
