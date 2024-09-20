package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RequestRataPlacanjeDTO {
    @NotNull
    @Size(min = 1)
    private List<Integer> rataIds;

    @NotNull
    @Size(max = 25)
    private String brojKartice;

    @NotNull
    @Size(max = 7)
    private String datumIsteka;

    @NotNull
    @Size(max = 3)
    private String cvv;
}
