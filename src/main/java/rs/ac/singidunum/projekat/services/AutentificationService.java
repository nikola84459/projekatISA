package rs.ac.singidunum.projekat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.dtos.RequestDTO;
import rs.ac.singidunum.projekat.dtos.ResponseDTO;
import rs.ac.singidunum.projekat.enums.RoleEnum;
import rs.ac.singidunum.projekat.models.ProfesorModel;
import rs.ac.singidunum.projekat.models.ProfesorTokenModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.StudentTokenModel;
import rs.ac.singidunum.projekat.repositories.IProfesorRepository;
import rs.ac.singidunum.projekat.repositories.IProfesorTokenRepository;
import rs.ac.singidunum.projekat.repositories.IStudentRepository;
import rs.ac.singidunum.projekat.repositories.IStudentTokenRepository;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class AutentificationService implements IAutentificationService {

    @Autowired
    private IStudentRepository iStudentRepository;

    @Autowired
    private AuthenticationProvider studentAuthenticationProvider;

    @Autowired
    private AuthenticationProvider profesorAuthenticationProvider;

    @Autowired
    private IProfesorRepository iProfesorRepository;

    @Autowired
    private IStudentTokenRepository studentTokenRepository;

    @Autowired
    private IProfesorTokenRepository profesorTokenRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public ResponseDTO loginStudent(RequestDTO request) {
        studentAuthenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        StudentModel student = iStudentRepository.findByBrIndeksa(request.getUsername());

        if(student == null) {
            throw new UsernameNotFoundException("Student sa unetim korisničkim imenom nije pronadjen.");
        }

        String authToken = jwtService.generateToken(student, RoleEnum.Student);
        String refreshToken = jwtService.generateRefreshToken(student, RoleEnum.Student);

        setIsNevazeciStudentToken(student);
        saveStudentToken(student, authToken);

        return ResponseDTO.builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .uloga(RoleEnum.Student.name())
                .build();
    }

    private void saveStudentToken(StudentModel student, String token) {
        StudentTokenModel studentToken = StudentTokenModel.builder()
                .student(student)
                .token(token)
                .isIstekao(false)
                .isNevazeci(false)
                .build();
        studentTokenRepository.save(studentToken);
    }

    private void setIsNevazeciStudentToken(StudentModel student) {
        List<StudentTokenModel> studentToken = studentTokenRepository.findByStudentStudentIdAndIsNevazeciFalseAndIsIstekaoFalse(student.getStudentId());

        if(studentToken.isEmpty()) {
            return;
        }

        studentToken.forEach(token -> {
            token.setIstekao(true);
            token.setNevazeci(true);
        });

        studentTokenRepository.saveAll(studentToken);
    }


    @Override
    public ResponseDTO loginProfesor(RequestDTO request) {
        profesorAuthenticationProvider.authenticate(
               new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        ProfesorModel profesor = iProfesorRepository.findByKime(request.getUsername());

        if(profesor == null) {
            throw new UsernameNotFoundException("Profesor sa unetim korisničkim imenom nije pronadjen.");
        }

        String authToken = jwtService.generateToken(profesor, RoleEnum.Profesor);
        String refreshToken = jwtService.generateRefreshToken(profesor, RoleEnum.Profesor);

        setIsNevazeciProfesorToken(profesor);
        saveProfesorToken(profesor, authToken);

        return ResponseDTO.builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .uloga(RoleEnum.Profesor.name())
                .build();

    }

    private void saveProfesorToken(ProfesorModel profesor, String token) {
        ProfesorTokenModel studentToken = ProfesorTokenModel.builder()
                .profesor(profesor)
                .token(token)
                .isIstekao(false)
                .isNevazeci(false)
                .build();
        profesorTokenRepository.save(studentToken);
    }

    private void setIsNevazeciProfesorToken(ProfesorModel profesor) {
        List<ProfesorTokenModel> profesorToken = profesorTokenRepository.findByProfesorProfesorIdAndIsNevazeciFalseAndIsIstekaoFalse(profesor.getProfesorId());

        if(profesorToken.isEmpty()) {
            return;
        }

        profesorToken.forEach(token -> {
            token.setIstekao(true);
            token.setNevazeci(true);
        });

        profesorTokenRepository.saveAll(profesorToken);
    }


    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUserName(refreshToken);
        String role = jwtService.extractUserRole(refreshToken);

        if(username != null) {
            UserDetails user = null;

            if (role.equals("Student")) {
                user = iStudentRepository.findByBrIndeksa(username);
            } else if (role.equals("Profesor")) {
                user = iProfesorRepository.findByKime(username);
            }

            if(user != null) {
               if(jwtService.isTokenValid(refreshToken, user)) {
                   String authToken = jwtService.generateToken(user, RoleEnum.valueOf(role));
                   if(user instanceof StudentModel) {
                       setIsNevazeciStudentToken((StudentModel) user);
                       saveStudentToken((StudentModel) user, authToken);
                   } else {
                       setIsNevazeciProfesorToken((ProfesorModel) user);
                       saveProfesorToken((ProfesorModel) user, authToken);
                   }

                   ResponseDTO authResponse = ResponseDTO.builder()
                            .authToken(authToken)
                            .refreshToken(refreshToken)
                            .build();
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        }
    }
}