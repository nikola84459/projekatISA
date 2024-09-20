package rs.ac.singidunum.projekat.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.models.ProfesorTokenModel;
import rs.ac.singidunum.projekat.models.StudentTokenModel;
import rs.ac.singidunum.projekat.repositories.IProfesorTokenRepository;
import rs.ac.singidunum.projekat.repositories.IStudentTokenRepository;

@Service
public class LogoutService implements LogoutHandler {
    @Autowired
    private IStudentTokenRepository iStudentTokenRepository;

    @Autowired
    private IProfesorTokenRepository iProfesorTokenRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);

        if (jwtService.extractUserRole(jwt).equals("Student")) {
           studentLogout(jwt);
        } else if (jwtService.extractUserRole(jwt).equals("Profesor")) {
            profesorLogout(jwt);
        }
    }

    private void studentLogout(String jwt) {
        StudentTokenModel studentToken = iStudentTokenRepository.findByToken(jwt).orElse(null);

        if(studentToken != null) {
            studentToken.setNevazeci(true);
            studentToken.setIstekao(true);
            iStudentTokenRepository.save(studentToken);
            SecurityContextHolder.clearContext();
        }
    }

    private void profesorLogout(String jwt) {
        ProfesorTokenModel profesorToken = iProfesorTokenRepository.findByToken(jwt).orElse(null);

        if(profesorToken != null) {
            profesorToken.setNevazeci(true);
            profesorToken.setIstekao(true);
            iProfesorTokenRepository.save(profesorToken);
            SecurityContextHolder.clearContext();
        }
    }
}
