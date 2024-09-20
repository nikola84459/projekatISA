package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.ObavestenjeModel;

import java.time.LocalDate;
import java.util.List;

public interface IObavestenjeRepository extends JpaRepository<ObavestenjeModel, Integer> {
    List<ObavestenjeModel> findByDatumIstekaLessThan(LocalDate datum);

    List<ObavestenjeModel> findByDatumIstekaGreaterThan(LocalDate datum);
}
