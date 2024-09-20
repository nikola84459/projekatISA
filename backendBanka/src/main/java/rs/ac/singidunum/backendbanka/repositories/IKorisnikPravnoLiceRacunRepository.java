package rs.ac.singidunum.backendbanka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.backendbanka.models.KorisnikPravnoLiceRacunModel;

public interface IKorisnikPravnoLiceRacunRepository extends JpaRepository<KorisnikPravnoLiceRacunModel, Integer> {
    KorisnikPravnoLiceRacunModel findByRacunBrRacuna(String brRacuna);
}
