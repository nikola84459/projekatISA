package rs.ac.singidunum.projekat.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlacanjeRequest {
    private String brojKartice;
    private String datumIsteka;
    private String cvv;
    private double iznos;
    private String brojRacuna;
    private String fakultetBroj;
}
