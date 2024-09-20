package rs.ac.singidunum.backendplacanje.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.backendplacanje.dtos.PlacanjeDTO;
import rs.ac.singidunum.backendplacanje.dtos.PodaciDTO;
import rs.ac.singidunum.backendplacanje.services.IPlacanjeService;


@RestController
@RequestMapping("placanje")
public class PlacanjeController {

    @Autowired
    private IPlacanjeService iPlacanjeService;

    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping ("plati")
    public ResponseEntity<?> bankaKomunikacija(@RequestBody PodaciDTO podaci) {
        PlacanjeDTO placanje = iPlacanjeService.bankaKomunikacija(podaci);

        if(placanje == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(placanje);
    }
}