package rs.ac.singidunum.projekat.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.ac.singidunum.projekat.constants.BrojConstants;
import rs.ac.singidunum.projekat.request.PlacanjeRequest;
import rs.ac.singidunum.projekat.response.PlacanjeResponse;


@Service
public class PlacanjeService implements IPlacanjeService{

    @Override
    public PlacanjeResponse bankaKonekcija(String brojKartice, String datumIsteka, String cvv, double iznos, String brojRacuna) {
            final String broj = BrojConstants.broj;

            RestTemplate restTemplate = new RestTemplate();
            

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            PlacanjeRequest placanjeRequest = PlacanjeRequest.builder()
                            .brojKartice(brojKartice)
                            .datumIsteka(datumIsteka)
                            .cvv(cvv)
                            .iznos(iznos)
                            .brojRacuna(brojRacuna)
                            .fakultetBroj(broj)
                            .build();


            HttpEntity<PlacanjeRequest> entity = new HttpEntity<>(placanjeRequest, headers);

            PlacanjeResponse placanje = null;

            try {
                ResponseEntity<PlacanjeResponse> responseEntity = restTemplate.postForEntity(
                        "http://localhost:8081/placanje/plati",
                        entity,
                        PlacanjeResponse.class
                );

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    placanje = responseEntity.getBody();
                }
            } catch (HttpClientErrorException err) {
                placanje = err.getResponseBodyAs(PlacanjeResponse.class);
            } catch (Exception e) {
                placanje = PlacanjeResponse.builder()
                                .isUspesno(false)
                                .poruka(e.getMessage())
                                .build();

            }

            return placanje;
    }
}