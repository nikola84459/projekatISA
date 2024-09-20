package rs.ac.singidunum.projekat.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.projekat.dtos.RequestDTO;
import rs.ac.singidunum.projekat.dtos.ResponseDTO;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.services.IAutentificationService;

import java.security.Principal;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private IAutentificationService iAutentificationService;

    @Autowired
    private UserDetailsService studentDetailsService;


    @PostMapping("loginStudent")
    public ResponseEntity<?> loginStudent(@RequestBody RequestDTO request) {
        try {
            ResponseDTO response = iAutentificationService.loginStudent(request);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException u) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(u.getMessage());
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Uneta šifra nije ispravna.");
        }
    }

    @PostMapping("loginProfesor")
    public ResponseEntity<?> loginProfesor(@RequestBody RequestDTO request) {
        try {
            ResponseDTO response = iAutentificationService.loginProfesor(request);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException u) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(u.getMessage());
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Uneta šifra nije ispravna.");
        }
    }

    @PostMapping("refresh_token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
       iAutentificationService.refreshToken(request, response);
    }
}