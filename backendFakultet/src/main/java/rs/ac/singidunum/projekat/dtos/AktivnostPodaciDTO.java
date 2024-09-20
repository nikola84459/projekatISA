package rs.ac.singidunum.projekat.dtos;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AktivnostPodaciDTO {
    @NotNull
    @Min(value = 1)
    private int studentPredmetId;

    @Min(value = 0)
    @Max(value = 10)
    private Integer aktivnost;

    @Min(value = 0)
    @Max(value = 30)
    private Integer prviKolokvijum;

    @Min(value = 0)
    @Max(value = 30)
    private Integer drugiKolokvijum;
}
