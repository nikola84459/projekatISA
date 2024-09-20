package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUpdateStanjeDTO {

    @NotNull
    @Size(max = 25)
    private String brojKartice;

    @NotNull
    @Size(max = 7)
    private String datumIsteka;

    @NotNull
    @Size(max = 3)
    private String cvv;

    @NotNull
    @Min(value = 1)
    private double iznos;
}
