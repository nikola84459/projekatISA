package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.PredmetModel;

import java.util.List;

public interface IPredmetRepository extends JpaRepository<PredmetModel, Integer> {
    List<PredmetModel> findByProfesorProfesorId(int profesorId);

    boolean existsByPredmetIdAndProfesorProfesorId(int predmetId, int profesorId);
}
