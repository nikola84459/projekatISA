package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.singidunum.projekat.models.IspitniRokModel;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IspitDTO {
    private int ispitniRokId;
    private String ispitniRokNaziv;
    private String predmetNaziv;
    private LocalDateTime vremeIspita;
    private int cenaPrijave;
    private boolean isRedovnaPrijava;

}
