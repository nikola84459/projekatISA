package rs.ac.singidunum.projekat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.projekat.dtos.PromenaSifreDTO;
import rs.ac.singidunum.projekat.models.ProfesorModel;
import rs.ac.singidunum.projekat.services.IProfesorService;

@RestController
@RequestMapping("profesor")
public class ProfesorController {
    @Autowired
    private IProfesorService iProfesorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("getById")
    public ResponseEntity<ProfesorModel> getProfesorById() {
        ProfesorModel profesor = iProfesorService.getProfesorById(iProfesorService.getPrijavljen());

        if(profesor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profesor);
    }

    @PostMapping("promenaSifreProfesor")
    public ResponseEntity<?> promenaSifre(@RequestBody PromenaSifreDTO request) {
        try{
            boolean promenaSifre = iProfesorService.promenaSifre(passwordEncoder,request.getStaraSifra(), request.getSifra(), request.getPonovoNovaSifra());

            if(!promenaSifre) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(true);
        } catch (IllegalArgumentException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }
}