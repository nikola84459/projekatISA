package rs.ac.singidunum.backendbanka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.backendbanka.models.TransakcijaModel;

public interface ITransakcijaRepository extends JpaRepository<TransakcijaModel, Integer> {
}
