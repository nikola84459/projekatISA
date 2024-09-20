package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.ProfesorModel;

public interface IProfesorRepository extends JpaRepository<ProfesorModel, Integer> {
    ProfesorModel findByKime(String kime);
}
