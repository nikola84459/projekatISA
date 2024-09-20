package rs.ac.singidunum.projekat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlacanjeResponse {
    private boolean isUspesno;
    private String poruka;
    private String brojTransakcije;
}
