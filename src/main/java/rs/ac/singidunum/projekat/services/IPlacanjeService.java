package rs.ac.singidunum.projekat.services;

import rs.ac.singidunum.projekat.response.PlacanjeResponse;

import java.util.List;

public interface IPlacanjeService {

    PlacanjeResponse bankaKonekcija(String brojKartice, String datumIsteka, String cvv, double iznos, String brojRacuna);
}
