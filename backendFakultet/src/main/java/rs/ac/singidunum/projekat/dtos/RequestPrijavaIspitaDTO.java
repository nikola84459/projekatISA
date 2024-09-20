package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPrijavaIspitaDTO {
    @NotNull
    @Min(value = 1)
    int ispitniRokId;

    @NotNull
    @Min(value = 1)
    int predmetId;
}
