package rs.ac.singidunum.backendplacanje.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.ac.singidunum.backendplacanje.dtos.PlacanjeDTO;
import rs.ac.singidunum.backendplacanje.dtos.PodaciDTO;
import rs.ac.singidunum.backendplacanje.models.BankaModel;
import rs.ac.singidunum.backendplacanje.repositories.IBankaRepository;
import rs.ac.singidunum.backendplacanje.response.PlacanjeResponse;

import java.util.UUID;

@Service
public class PlacanjeService implements IPlacanjeService{

    @Autowired
    private IBankaRepository iBankaRepository;

    @Autowired
    private ITransakcijaService iTransakcijaService;

    @Override
    public PlacanjeDTO bankaKomunikacija(PodaciDTO podaci) {
        String bankaBroj = podaci.getBrojKartice().substring(0, 6);

        BankaModel banka = iBankaRepository.findByBroj(bankaBroj);

        if(banka == null) {
           return null;
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PodaciDTO> entity = new HttpEntity<>(podaci, headers);

        String brojTransakcije = UUID.randomUUID().toString();

        PlacanjeDTO placanjeDTO = null;

        try {
            ResponseEntity<PlacanjeResponse> responseEntity = restTemplate.postForEntity(
                    banka.getLink(),
                    entity,
                    PlacanjeResponse.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                iTransakcijaService.addTransakcija(brojTransakcije, podaci.getBrojKartice(), podaci.getIznos(), true, banka, responseEntity.getBody().getBrojTransakcije());
            } else {
                iTransakcijaService.addTransakcija(brojTransakcije, podaci.getBrojKartice(), podaci.getIznos(), false, banka, null);
            }
            placanjeDTO = PlacanjeDTO.builder()
                            .isUspesno(responseEntity.getBody().isUspesno())
                            .poruka(responseEntity.getBody().getPoruka())
                            .brojTransakcije(brojTransakcije)
                            .build();

        } catch (HttpClientErrorException err) {
            PlacanjeResponse p = err.getResponseBodyAs(PlacanjeResponse.class);
            placanjeDTO = PlacanjeDTO.builder()
                    .isUspesno(p.isUspesno())
                    .poruka(p.getPoruka())
                    .brojTransakcije(brojTransakcije)
                    .build();

        } catch (Exception e) {
            placanjeDTO = PlacanjeDTO.builder()
                    .isUspesno(false)
                    .poruka(e.getMessage())
                    .build();

        }

        return placanjeDTO;
    }
}