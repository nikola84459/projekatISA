package rs.ac.singidunum.backendplacanje.dtos;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PodaciDTO {
    private String brojKartice;
    private String datumIsteka;
    private String cvv;
    private double iznos;
    private String brojRacuna;
    private String fakultetBroj;
}
