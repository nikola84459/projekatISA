package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PromenaSifreDTO {
    @NotNull
    @Size(min = 8, max = 100)
    private String staraSifra;

    @NotNull
    @Size(min = 8, max = 100)
    private String sifra;

    @NotNull
    @Size(min = 8, max = 100)
    private String ponovoNovaSifra;
}
