package rs.ac.singidunum.projekat.controllers;

import jdk.javadoc.doclet.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.singidunum.projekat.models.ObavestenjeModel;
import rs.ac.singidunum.projekat.repositories.IObavestenjeRepository;
import rs.ac.singidunum.projekat.services.IObavestenjeService;
import rs.ac.singidunum.projekat.services.ObavestenjeService;

import java.util.List;

@RestController
@RequestMapping("obavestenje")
public class ObavestenjeControllor {

    @Autowired
    private IObavestenjeService iObavestenjeService;

    @GetMapping("getObavestenje")
    public ResponseEntity<List<ObavestenjeModel>> getObavestenje() {
        List<ObavestenjeModel> obavestenje = iObavestenjeService.getObavestenje();

        if(obavestenje == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(obavestenje);
    }

    @GetMapping("getStaroObavestenje")
    public ResponseEntity<List<ObavestenjeModel>> getStaroObavestenje() {
        List<ObavestenjeModel> obavestenje = iObavestenjeService.getStaroObavestenje();

        if(obavestenje == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(obavestenje);
    }
}
