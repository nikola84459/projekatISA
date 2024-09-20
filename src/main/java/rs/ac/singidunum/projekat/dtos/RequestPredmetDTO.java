package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPredmetDTO {
    @NotNull
    @Min(value = 1)
    int predmetId;
}
