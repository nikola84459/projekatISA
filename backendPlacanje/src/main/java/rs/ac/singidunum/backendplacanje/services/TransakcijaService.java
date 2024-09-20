package rs.ac.singidunum.backendplacanje.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.backendplacanje.models.BankaModel;
import rs.ac.singidunum.backendplacanje.models.TransakcijaModel;
import rs.ac.singidunum.backendplacanje.repositories.ITransakcijaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransakcijaService implements ITransakcijaService{

    @Autowired
    private ITransakcijaRepository iTransakcijaRepository;

    @Override
    public TransakcijaModel addTransakcija(String brojTransakcije, String brojKartice, double iznos, boolean isUspesna, BankaModel banka, String bankaBrTransakcije) {
        TransakcijaModel transakcija = TransakcijaModel.builder()
                        .brojTransakcije(brojTransakcije)
                        .brojKartice(brojKartice)
                        .datumIVreme(LocalDateTime.now())
                        .iznos(iznos)
                        .isUspesna(isUspesna)
                        .banka(banka)
                        .bankaBrTransakcije(bankaBrTransakcije)
                        .build();

        return iTransakcijaRepository.save(transakcija);
    }
}
