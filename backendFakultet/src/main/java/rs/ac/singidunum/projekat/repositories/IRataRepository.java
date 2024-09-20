package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.RataModel;

import java.util.List;

public interface IRataRepository extends JpaRepository<RataModel, Integer> {
    List<RataModel> findByStudentStudentIdOrderByRokPlacanjaAsc(int studentId);
}
