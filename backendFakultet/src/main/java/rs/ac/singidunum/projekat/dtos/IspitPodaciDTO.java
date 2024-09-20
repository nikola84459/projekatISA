package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IspitPodaciDTO {
    @NotNull
    @Min(value = 1)
    private Integer ispitPrijavaId;

    @Max(value = 10)
    private Integer aktivnost;


    @Max(value = 30)
    private Integer prviKolokvijum;


    @Max(value = 30)
    private Integer drugiKolokvijum;


    @Max(value = 30)
    private Integer ispitBodovi;


    @Max(value = 10)
    private Integer ispitOcena;
}
