package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.ProfesorTokenModel;
import rs.ac.singidunum.projekat.models.StudentTokenModel;

import java.util.List;
import java.util.Optional;

public interface IProfesorTokenRepository extends JpaRepository<ProfesorTokenModel, Integer> {
    List<ProfesorTokenModel> findByProfesorProfesorIdAndIsNevazeciFalseAndIsIstekaoFalse(int studentId);

    Optional<ProfesorTokenModel> findByToken(String token);
}
