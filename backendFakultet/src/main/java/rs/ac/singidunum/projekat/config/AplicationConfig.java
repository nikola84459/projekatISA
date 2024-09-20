package rs.ac.singidunum.projekat.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.singidunum.projekat.models.ProfesorModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.services.IProfesorService;
import rs.ac.singidunum.projekat.services.IStudentService;


@Configuration
@RequiredArgsConstructor
public class AplicationConfig {

    private final IStudentService iStudentService;
    private final IProfesorService iProfesorService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService studentDetailsService() {
        return username -> {
            StudentModel student = iStudentService.getByBrIndeksa(username);

            if(student == null) {
                throw new UsernameNotFoundException("Student sa unetim korisničkim imenom nije pronadjen.");
            }

            return student;
        };
    }

    @Bean
    public UserDetailsService profesorDetailsService() {
        return username -> {
            ProfesorModel profesor = iProfesorService.getProfesorByKime(username);

            if(profesor == null) {
                throw new UsernameNotFoundException("Profesor sa unetim korisničkim imenom nije pronadjen.");
            }

            return profesor;
        };
    }

    @Bean
    public AuthenticationProvider studentAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(studentDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public AuthenticationProvider profesorAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(profesorDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}