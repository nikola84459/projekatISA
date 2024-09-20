package rs.ac.singidunum.projekat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.projekat.dtos.RataDTO;
import rs.ac.singidunum.projekat.dtos.RequestRataPlacanjeDTO;
import rs.ac.singidunum.projekat.models.RataModel;
import rs.ac.singidunum.projekat.response.PlacanjeResponse;
import rs.ac.singidunum.projekat.services.IPlacanjeService;
import rs.ac.singidunum.projekat.services.IRataService;
import rs.ac.singidunum.projekat.services.IStudentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("rata")
public class RataController {

    @Autowired
    private IRataService iRataService;

    @Autowired
    private IStudentService iStudentService;


    @GetMapping("getAllForStudent")
    public ResponseEntity<RataDTO> getAllForStudent() {
        int studentId = iStudentService.getPrijavljen();

        RataDTO rata = iRataService.getAllForStudent(studentId);

        if(rata == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rata);
    }

    @PostMapping("platiSkolarinu")
    public ResponseEntity<?> platiSkolarinu(@RequestBody RequestRataPlacanjeDTO request) {
        PlacanjeResponse placanjeResponse = iRataService.platiSkolarinu(request.getBrojKartice(), request.getDatumIsteka(), request.getCvv(), request.getRataIds(), iStudentService.getPrijavljen());

        if(placanjeResponse == null) {
            return ResponseEntity.notFound().build();
        }

        if(!placanjeResponse.isUspesno()) {
            return ResponseEntity.badRequest().body(placanjeResponse);
        }

        return ResponseEntity.ok(placanjeResponse);
    }
}