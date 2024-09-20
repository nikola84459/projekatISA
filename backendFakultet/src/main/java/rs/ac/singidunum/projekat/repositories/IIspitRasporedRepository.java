package rs.ac.singidunum.projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.projekat.models.IspitRaspored;

public interface IIspitRasporedRepository  extends JpaRepository<IspitRaspored, Integer> {
    IspitRaspored getIspitRasporedByIspitniRokIspitniRokIdAndPredmetPredmetId(int ispitniRokId, int predmetId);
}
