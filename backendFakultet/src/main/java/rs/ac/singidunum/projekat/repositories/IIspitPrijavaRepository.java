package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.singidunum.projekat.models.IspitPrijavaModel;
import rs.ac.singidunum.projekat.models.IspitniRokModel;

import java.util.List;

public interface IIspitPrijavaRepository extends JpaRepository<IspitPrijavaModel, Integer> {
    boolean existsByStudentPredmetStudentStudentIdAndIspitniRokIspitniRokIdAndStudentPredmetPredmetPredmetIdAndIsZakljucen(int studentId, int ispitniRokId, int predmetId, boolean isZakljucen);

    List<IspitPrijavaModel> findByStudentPredmetPredmetPredmetIdAndIspitniRokIspitniRokIdAndIsZakljucen(int predmetId, int ispitniRokId, boolean isZakljucen);
    List<IspitPrijavaModel> findByStudentPredmetStudentStudentIdAndIsZakljucen (int studentId, boolean isZakljucen);

    List<IspitPrijavaModel> findByStudentPredmetStudentStudentIdAndIsPolozenAndIsZakljucen(int studentId, boolean isPolozen, boolean isZakljucen);

    @Query("SELECT DISTINCT ip.ispitniRok FROM IspitPrijavaModel ip WHERE ip.isZakljucen = :isZakljucen AND ip.studentPredmet.predmet.predmetId = :predmetId")
    List<IspitniRokModel> findByisZakljuceniIspitPrijava(@Param("predmetId") int predmetId, @Param("isZakljucen") boolean isZakljucen);
}
