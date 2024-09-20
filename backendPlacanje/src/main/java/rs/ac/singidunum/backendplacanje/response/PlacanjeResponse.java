package rs.ac.singidunum.backendplacanje.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlacanjeResponse {
    private boolean isUspesno;
    private String poruka;
    private String brojTransakcije;
}
