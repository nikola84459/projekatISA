package rs.ac.singidunum.backendbanka.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.singidunum.backendbanka.dtos.PodaciDTO;
import rs.ac.singidunum.backendbanka.dtos.TransakcijaDTO;
import rs.ac.singidunum.backendbanka.models.KarticaModel;
import rs.ac.singidunum.backendbanka.models.KorisnikPravnoLiceRacunModel;
import rs.ac.singidunum.backendbanka.models.TransakcijaModel;
import rs.ac.singidunum.backendbanka.repositories.IKarticaRepository;
import rs.ac.singidunum.backendbanka.repositories.IKorisnikPravnoLiceRacunRepository;
import rs.ac.singidunum.backendbanka.repositories.IRacunRepository;
import rs.ac.singidunum.backendbanka.repositories.ITransakcijaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransakcijaService implements ITransakcijaService{
    @Autowired
    private IKarticaRepository iKarticaRepository;

    @Autowired
    private IRacunRepository iRacunRepository;

    @Autowired
    private ITransakcijaRepository iTransakcijaRepository;

    @Autowired
    private IKorisnikPravnoLiceRacunRepository iKorisnikPravnoLiceRacunRepository;

    
    @Override
    @Transactional
    public TransakcijaDTO transakcija(PodaciDTO podaci) {
        KarticaModel kartica = iKarticaRepository.findByBrojKarticeAndDatumIstekaAndAndCvv(podaci.getBrojKartice(), podaci.getDatumIsteka(), podaci.getCvv());
        KorisnikPravnoLiceRacunModel korisnikPravnoLiceRacunModel = iKorisnikPravnoLiceRacunRepository.findByRacunBrRacuna(podaci.getBrojRacuna());

        TransakcijaDTO transakcijaDTO = null;

        if(kartica == null) {
            transakcijaDTO = TransakcijaDTO.builder()
                            .isUspesno(false)
                            .poruka("Uneti podaci sa kartice nisu ispravni.")
                            .build();

            return transakcijaDTO;
        }

        if(kartica.getRacun().getStanje() < podaci.getIznos()) {
            transakcijaDTO = TransakcijaDTO.builder()
                            .isUspesno(false)
                            .poruka("Nemate dovoljno novca na računu kako bi se izvršila transakcija.")
                            .build();

            return transakcijaDTO;
        }

        if(korisnikPravnoLiceRacunModel == null) {
            transakcijaDTO = TransakcijaDTO.builder()
                    .isUspesno(false)
                    .poruka("Nije moguće izvršiti plaćanje.")
                    .build();

            return transakcijaDTO;
        }

        if(!korisnikPravnoLiceRacunModel.getKorisnikPravnoLice().getBroj().equals(podaci.getFakultetBroj())) {
            transakcijaDTO = TransakcijaDTO.builder()
                    .isUspesno(false)
                    .poruka("Nije moguće izvršiti plaćanje.")
                    .build();

            return transakcijaDTO;
        }

        kartica.getRacun().setStanje(kartica.getRacun().getStanje() - podaci.getIznos());
        iRacunRepository.save(kartica.getRacun());

        double stanjePravnoLice = korisnikPravnoLiceRacunModel.getRacun().getStanje();
        double novoStanje = stanjePravnoLice + podaci.getIznos();
        korisnikPravnoLiceRacunModel.getRacun().setStanje(novoStanje);
        iRacunRepository.save(korisnikPravnoLiceRacunModel.getRacun());

        String brojTransakcije = UUID.randomUUID().toString();

        TransakcijaModel transakcija = TransakcijaModel.builder()
                        .broj(brojTransakcije)
                        .datumIVreme(LocalDateTime.now())
                        .kartica(kartica)
                        .iznos(podaci.getIznos())
                        .build();

        iTransakcijaRepository.save(transakcija);
        transakcijaDTO = TransakcijaDTO.builder()
                        .isUspesno(true)
                        .poruka(null)
                        .brojTransakcije(brojTransakcije)
                        .build();

        return transakcijaDTO;
    }
}
