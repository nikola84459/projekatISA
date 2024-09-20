package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.UpisModel;

public interface IUpisRepository extends JpaRepository<UpisModel, Integer> {
    UpisModel findByStudentStudentIdAndIsAktivan(int studentId, boolean isAktivan);
}
