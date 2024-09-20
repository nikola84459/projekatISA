package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.singidunum.projekat.models.IspitniRokModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IIspitniRokRepository extends JpaRepository<IspitniRokModel, Integer> {

    List<IspitniRokModel> findByPocetakPrijaveLessThanEqualAndKrajGreaterThanEqual(LocalDate datumPocetkaPrijave, LocalDate krajPrijave);

    boolean existsByPocetakPrijaveLessThanEqualAndKrajGreaterThanEqualAndIspitniRokId(LocalDate datumPocetkaPrijave, LocalDate krajPrijave, int ispitniRokId);

    List<IspitniRokModel> findByIspitPrijavaStudentPredmetPredmetPredmetIdAndIspitPrijavaIsZakljucen(int predmetId, boolean isZakljucen);
}
