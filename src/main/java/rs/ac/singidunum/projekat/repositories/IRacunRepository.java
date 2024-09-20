package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.RacunModel;

public interface IRacunRepository extends JpaRepository<RacunModel, Integer> {
    RacunModel findBySmerSmerId(int smerId);
}
