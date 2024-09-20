package rs.ac.singidunum.projekat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.singidunum.projekat.models.RataModel;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RataDTO {
    List<RataModel> rata;
    double ukupnoDugovanje;
    double dugovanjeNaDanasnjiDan;
    double ukupnoUplaceno;
}
