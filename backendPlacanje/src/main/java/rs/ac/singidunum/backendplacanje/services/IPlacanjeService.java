package rs.ac.singidunum.backendplacanje.services;

import rs.ac.singidunum.backendplacanje.dtos.PlacanjeDTO;
import rs.ac.singidunum.backendplacanje.dtos.PodaciDTO;

public interface IPlacanjeService {
    PlacanjeDTO bankaKomunikacija(PodaciDTO podaci);
}
