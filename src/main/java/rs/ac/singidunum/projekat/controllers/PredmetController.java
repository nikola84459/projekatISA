package rs.ac.singidunum.projekat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.projekat.dtos.AktivnostPodaciDTO;
import rs.ac.singidunum.projekat.dtos.RequestAktivnostAllDTO;
import rs.ac.singidunum.projekat.dtos.RequestPredmetDTO;
import rs.ac.singidunum.projekat.models.PredmetModel;
import rs.ac.singidunum.projekat.models.ProfesorModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.StudentPredmet;
import rs.ac.singidunum.projekat.services.IPredmetService;
import rs.ac.singidunum.projekat.services.IProfesorService;
import rs.ac.singidunum.projekat.services.IStudentService;
import rs.ac.singidunum.projekat.services.PredmetService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("predmet")
public class PredmetController {

    @Autowired
    private IPredmetService iPredmetService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IProfesorService iProfesorService;


    @GetMapping("getPredmetForStudent")
    public ResponseEntity<List<StudentPredmet>> getPredmetForStudent() {
        int studentId = iStudentService.getPrijavljen();

        List<StudentPredmet> predmeti = iPredmetService.getAllForStudent(studentId);

        if(predmeti != null) {
            return ResponseEntity.ok(predmeti);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("getAktivnost")
    public ResponseEntity<StudentPredmet> getAktivnost(@RequestBody RequestPredmetDTO request) {
        int studentId = iStudentService.getPrijavljen();

        StudentPredmet aktivnost = iPredmetService.getPredmetForStudent(studentId, request.getPredmetId());

        if(aktivnost != null) {
            return ResponseEntity.ok(aktivnost);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("getPredmetForProfesor")
    public ResponseEntity<List<PredmetModel>> getPredmetForProfesor() {
        int profesorId = iProfesorService.getPrijavljen();

        List<PredmetModel> predmet = iPredmetService.getPredmetForProfesor(profesorId);

        if(predmet == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(predmet);
    }

    @PostMapping("getUpisani")
    public ResponseEntity<List<StudentPredmet>> getUpisaniForProfesor(@RequestBody RequestPredmetDTO request) {
        int profesorId = iProfesorService.getPrijavljen();

        List<StudentPredmet> upisaniStudenti = iPredmetService.getUpisaniForProfesor(request.getPredmetId(), profesorId);

        if(upisaniStudenti == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(upisaniStudenti);
    }


    @PostMapping("addAktivnost")
    public ResponseEntity<?> addAktivnostAll(@RequestBody RequestAktivnostAllDTO request) {
        int profesorId = iProfesorService.getPrijavljen();

        boolean aktivnost = iPredmetService.aktivnostUnos(request.getAktivnostPodaci(), profesorId);

        if(!aktivnost) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Došlo je do greške prilikom ažuriranja podataka.");
        }

        return ResponseEntity.ok(true);
    }
}