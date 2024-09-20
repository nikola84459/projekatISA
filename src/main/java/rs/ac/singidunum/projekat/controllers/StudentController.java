package rs.ac.singidunum.projekat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.projekat.dtos.PromenaSifreDTO;
import rs.ac.singidunum.projekat.dtos.RequestUpdateStanjeDTO;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.UpisModel;
import rs.ac.singidunum.projekat.response.PlacanjeResponse;
import rs.ac.singidunum.projekat.services.IStudentService;
import rs.ac.singidunum.projekat.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("getStudentById")
    public ResponseEntity<StudentModel> getById() {
        StudentModel student = iStudentService.getById(iStudentService.getPrijavljen());

        if(student != null) {
            return ResponseEntity.ok(student);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("getStanjeNaRacunu")
    public ResponseEntity<Double> getStanjeNaRacunu() {
        double stanjeNaRacunu = iStudentService.getById(iStudentService.getPrijavljen()).getStanjeNaRacunu();
        return ResponseEntity.ok(stanjeNaRacunu);
    }

    @GetMapping("getUpis")
    public ResponseEntity<List<UpisModel>> getUpis() {
        List<UpisModel> upis = iStudentService.getUpis(iStudentService.getPrijavljen());
        if(upis == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(upis);
    }

    @PostMapping("promenaSifreStudent")
    public ResponseEntity<?> promenaSifre(@RequestBody PromenaSifreDTO request) {
        try{
            boolean promenaSifre = iStudentService.promenaSifre(passwordEncoder,request.getStaraSifra(), request.getSifra(), request.getPonovoNovaSifra());

            if(!promenaSifre) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(true);
        } catch (IllegalArgumentException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }


    @PostMapping("updateStanjeNaRacunu")
    public ResponseEntity<?> updateStanjeNaRacunu(@RequestBody RequestUpdateStanjeDTO request) {
        int studentId = iStudentService.getPrijavljen();

        PlacanjeResponse placanjeResponse = iStudentService.updateStanjeNaRacunu(request.getBrojKartice(),request.getDatumIsteka(), request.getCvv(), studentId, request.getIznos());

        if(placanjeResponse == null) {
            return ResponseEntity.notFound().build();
        }

        if(!placanjeResponse.isUspesno()) {
            return ResponseEntity.badRequest().body(placanjeResponse);
        }

        return ResponseEntity.ok(placanjeResponse);
    }
}