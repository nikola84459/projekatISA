package rs.ac.singidunum.backendplacanje.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlacanjeDTO {
    private boolean isUspesno;
    private String poruka;
    private String brojTransakcije;
}
