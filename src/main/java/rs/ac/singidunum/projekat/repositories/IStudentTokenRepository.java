package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.StudentTokenModel;

import java.util.List;
import java.util.Optional;

public interface IStudentTokenRepository extends JpaRepository<StudentTokenModel, Integer> {
    List<StudentTokenModel> findByStudentStudentIdAndIsNevazeciFalseAndIsIstekaoFalse(int studentId);

    Optional<StudentTokenModel> findByToken(String token);
}
