package rs.ac.singidunum.projekat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.projekat.dtos.*;
import rs.ac.singidunum.projekat.models.*;
import rs.ac.singidunum.projekat.services.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("ispit")
public class IspitController {

    @Autowired
    private IIspitService ispitService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IProfesorService iProfesorService;

    @GetMapping("getPrijavljenByStudentId")
    public ResponseEntity<List<IspitPrijavljeniDTO>>getPrijavljenForStudent() {
        List<IspitPrijavljeniDTO> ispitPrijava = ispitService.getPrijavljeniForStudent(iStudentService.getPrijavljen());

        if(ispitPrijava == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ispitPrijava);
    }

    @GetMapping("polozeniForStudent")
    public ResponseEntity<List<IspitPrijavaModel>> getPolozeni() {
        List<IspitPrijavaModel> polozeni = ispitService.getZakljuceniForStudent(iStudentService.getPrijavljen(), true);

        if(polozeni == null) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(polozeni);
    }

    @GetMapping("neuspesnaPolaganja")
    public ResponseEntity<List<IspitPrijavaModel>> getNePolozeni() {
        List<IspitPrijavaModel> polozeni = ispitService.getZakljuceniForStudent(iStudentService.getPrijavljen(), false);

        if(polozeni == null) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(polozeni);
    }

    @PostMapping("getNezakljuceniIspitniRok")
    public ResponseEntity<?> getNezakljuceniIspitniRok(@RequestBody RequestPredmetDTO request) {
        List<IspitniRokModel> ispitniRok = ispitService.getByIsZakljucen(request.getPredmetId(), false);

        if(ispitniRok == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Svi ispitni rokovi za ovaj predmet su zaključeni.");
        }

        return ResponseEntity.ok(ispitniRok);
    }

    @PostMapping("getZakljucenIspitniRok")
    public ResponseEntity<?> getZakljuceniIspitniRok(@RequestBody RequestPredmetDTO request) {
        List<IspitniRokModel> ispitniRok = ispitService.getByIsZakljucen(request.getPredmetId(), true);

        if(ispitniRok == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ne postoje zaključeni ispiti za ovaj predmet.");
        }

        return ResponseEntity.ok(ispitniRok);
    }


    @PostMapping("getIspitniRok")
    public ResponseEntity<?> getIspitniRok(@RequestBody RequestPredmetDTO request) {
        List<IspitniRokModel> ispitniRok = ispitService.getIspitniRok();
        
        if(ispitniRok == null) {
            return ResponseEntity.badRequest().body("Prijava ispita nije u toku.");
        }

        if(ispitniRok.size() == 1) {
            IspitDTO ispitDTO = ispitService.ispitDetalji(ispitniRok.get(0).getIspitniRokId(), request.getPredmetId());

            if(ispitDTO == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(ispitDTO);
        }

        return ResponseEntity.ok(ispitniRok);
    }

    @PostMapping("getIspitRaspored")
    public ResponseEntity<IspitDTO> getIspitRaspored(@RequestBody RequestIspitDTO request) {
        IspitDTO ispitDTO = ispitService.ispitDetalji(request.getIspitniRokId(), request.getPredmetId());
        return ResponseEntity.ok(ispitDTO);
    }

    @PostMapping("prijavaIspita")
    public ResponseEntity<?> prijavaIspita(@RequestBody RequestIspitDTO request) {
        int studentId = iStudentService.getPrijavljen();

        try{
            return ResponseEntity.ok(ispitService.prijavaIspita(studentId, request.getIspitniRokId(), request.getPredmetId()));
        } catch (IllegalArgumentException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Došo je do greške kod prijave ispita: " + e.getMessage());
        }
    }

    @PostMapping("isZakljucen")
    public ResponseEntity<List<IspitniRokModel>> isZakljucen(@RequestBody RequestPredmetDTO request) {
        List<IspitniRokModel> ispitniRok = ispitService.getZakljucen(request.getPredmetId());

        if(ispitniRok == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ispitniRok);
    }

    @PostMapping("addIspit")
    public ResponseEntity<String> addIspit(@RequestBody RequestAddIspitDTO request) {
        try {
            int profesorId = iProfesorService.getPrijavljen();

            boolean ispit = ispitService.addIspit(request.getIspitPodaci(), profesorId);

            if(!ispit) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Došlo je do greške");
            }

            return ResponseEntity.ok("Uspešno ste uneli rezultate ispita.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception err) {
            return ResponseEntity.badRequest().body("Došlo je do greške kod upisa u ispita: " + err.getMessage());
        }
    }

    @PostMapping("getPrijavljeni")
    public ResponseEntity<List<IspitPrijavaModel>> getPrijavljeni(@RequestBody RequestIspitDTO request) {
        List<IspitPrijavaModel> prijavljeni = ispitService.getPrijavljeni(request.getPredmetId(), request.getIspitniRokId());

        if(prijavljeni == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prijavljeni);
    }

    @PostMapping("getZakljuceniForProfesor")
    public ResponseEntity<List<IspitPrijavaModel>> getZakljuceniForProfesor(@RequestBody RequestIspitDTO request) {
        List<IspitPrijavaModel> zakljuceni = ispitService.getZakljuceniForProfesor(request.getPredmetId(), request.getIspitniRokId());

        if(zakljuceni == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(zakljuceni);
    }

    @PostMapping("setIsZakljucen")
    public ResponseEntity<String> setIsZakljucen(@RequestBody RequestSetZakljucenDTO request) {
        int profesorId = iProfesorService.getPrijavljen();

        boolean setIzZakljucen = ispitService.setIsZakljucen(request.getIspIds(), profesorId);

        if(!setIzZakljucen) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Uspešno ste zaključili sve prijave ispita.");
    }
}