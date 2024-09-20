package rs.ac.singidunum.projekat.dtos;

import lombok.Data;
import rs.ac.singidunum.projekat.models.IspitniRokModel;
import rs.ac.singidunum.projekat.models.PredmetModel;
import rs.ac.singidunum.projekat.models.StudentModel;

@Data
public class IspitPrijavaDTO {
    private StudentModel student;
    private PredmetModel predmet;
    private IspitniRokModel ispitniRok;
}
