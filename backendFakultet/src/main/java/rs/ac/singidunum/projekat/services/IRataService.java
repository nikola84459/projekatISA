package rs.ac.singidunum.projekat.services;

import rs.ac.singidunum.projekat.dtos.RataDTO;
import rs.ac.singidunum.projekat.models.RacunModel;
import rs.ac.singidunum.projekat.models.RataModel;
import rs.ac.singidunum.projekat.response.PlacanjeResponse;

import java.util.List;

public interface IRataService {
    RataDTO getAllForStudent(int studentId);

    List<RataModel> getRataForPlacanje(List<Integer> rataIds);

    RacunModel getRacun(int smerId);

    PlacanjeResponse platiSkolarinu(String brojKartice, String datumIsteka, String cvv, List<Integer> rataIds, int studentId);
}
