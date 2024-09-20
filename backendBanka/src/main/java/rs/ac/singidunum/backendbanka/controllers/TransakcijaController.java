package rs.ac.singidunum.backendbanka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.backendbanka.dtos.PodaciDTO;
import rs.ac.singidunum.backendbanka.dtos.TransakcijaDTO;
import rs.ac.singidunum.backendbanka.services.ITransakcijaService;

@RestController
@RequestMapping("placanje")
public class TransakcijaController {

    @Autowired
    ITransakcijaService iTransakcijaService;

    @PostMapping("transakcija")
    @CrossOrigin("http://localhost:8081/")
    public ResponseEntity<?> transakcija(@RequestBody PodaciDTO podaci) {
        TransakcijaDTO transakcija = iTransakcijaService.transakcija(podaci);

        if(!transakcija.isUspesno()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transakcija);
        }

        return ResponseEntity.ok(transakcija);
    }
}
