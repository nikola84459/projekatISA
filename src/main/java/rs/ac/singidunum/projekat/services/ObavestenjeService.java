package rs.ac.singidunum.projekat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.models.ObavestenjeModel;
import rs.ac.singidunum.projekat.repositories.IObavestenjeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ObavestenjeService implements IObavestenjeService {

    @Autowired
    private IObavestenjeRepository iObavestenjeRepository;

    @Override
    public List<ObavestenjeModel> getObavestenje() {
        List<ObavestenjeModel> obavestenje = iObavestenjeRepository.findByDatumIstekaGreaterThan(LocalDate.now());

        if(obavestenje.isEmpty()) {
            return null;
        }

        return obavestenje;
    }

    @Override
    public List<ObavestenjeModel> getStaroObavestenje() {
        List<ObavestenjeModel> obavestenje = iObavestenjeRepository.findByDatumIstekaLessThan(LocalDate.now());

        if(obavestenje.isEmpty()) {
            return null;
        }

        return obavestenje;
    }
}
