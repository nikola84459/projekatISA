package rs.ac.singidunum.backendbanka.services;

import rs.ac.singidunum.backendbanka.dtos.PodaciDTO;
import rs.ac.singidunum.backendbanka.dtos.TransakcijaDTO;

public interface ITransakcijaService {

    TransakcijaDTO transakcija(PodaciDTO podaci);
}
