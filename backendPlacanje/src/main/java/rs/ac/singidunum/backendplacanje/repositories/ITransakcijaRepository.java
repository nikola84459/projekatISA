package rs.ac.singidunum.backendplacanje.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.backendplacanje.models.TransakcijaModel;

public interface ITransakcijaRepository extends JpaRepository<TransakcijaModel, Integer> {
}
