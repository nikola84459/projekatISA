package rs.ac.singidunum.backendbanka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.backendbanka.models.KarticaModel;

public interface IKarticaRepository extends JpaRepository<KarticaModel, Integer> {
    KarticaModel findByBrojKarticeAndDatumIstekaAndAndCvv(String brojKartice, String datumIsteka, String cvv);
}
