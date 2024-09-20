package rs.ac.singidunum.projekat.dtos;

import lombok.Data;
import rs.ac.singidunum.projekat.models.IspitModel;
import rs.ac.singidunum.projekat.models.IspitniRokModel;
import rs.ac.singidunum.projekat.models.PredmetModel;

@Data
public class IspitPrijavljeniDTO {
    private PredmetModel predmet;
    private IspitniRokModel ispitniRok;
    private IspitModel ispit;
}
