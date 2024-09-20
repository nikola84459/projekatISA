package rs.ac.singidunum.backendplacanje.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.backendplacanje.models.BankaModel;

public interface IBankaRepository extends JpaRepository<BankaModel, Integer> {
    BankaModel findByBroj(String broj);
}
