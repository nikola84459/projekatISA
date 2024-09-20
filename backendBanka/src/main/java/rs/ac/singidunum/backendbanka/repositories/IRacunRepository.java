package rs.ac.singidunum.backendbanka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.backendbanka.models.RacunModel;

public interface IRacunRepository extends JpaRepository<RacunModel, Integer> {
}
