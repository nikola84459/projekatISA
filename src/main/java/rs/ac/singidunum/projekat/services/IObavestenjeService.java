package rs.ac.singidunum.projekat.services;

import rs.ac.singidunum.projekat.models.ObavestenjeModel;

import java.util.List;

public interface IObavestenjeService {
    List<ObavestenjeModel> getObavestenje();

    List<ObavestenjeModel> getStaroObavestenje();
}
