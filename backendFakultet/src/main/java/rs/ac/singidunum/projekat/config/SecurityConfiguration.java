package rs.ac.singidunum.projekat.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import rs.ac.singidunum.projekat.enums.PravaPristupa;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationProvider studentAuthenticationProvider;
    private final AuthenticationProvider profesorAuthenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       auth -> auth
                               .requestMatchers(HttpMethod.POST, "auth/loginStudent", "auth/loginProfesor", "auth/refresh_token").permitAll()
                               .requestMatchers(HttpMethod.GET, "ispit/getPrijavljenByStudentId").hasAuthority(PravaPristupa.StudentReadPrijavljeni.name())
                               .requestMatchers(HttpMethod.GET, "ispit/polozeniForStudent").hasAuthority(PravaPristupa.StudentReadPolozeni.name())
                               .requestMatchers(HttpMethod.GET, "ispit/neuspesnaPolaganja").hasAuthority(PravaPristupa.StudentReadNeuspesnaPolaganja.name())
                               .requestMatchers(HttpMethod.POST, "ispit/getIspitniRok").hasAuthority(PravaPristupa.StudentGetIspitniRok.name())
                               .requestMatchers(HttpMethod.POST, "ispit/getIspitRaspored").hasAnyAuthority(PravaPristupa.StudentGetIspitRaspored.name())
                               .requestMatchers(HttpMethod.POST, "ispit/prijavaIspita").hasAnyAuthority(PravaPristupa.StudentPrijavaIspita.name())
                               .requestMatchers(HttpMethod.POST, "ispit/addIspit").hasAnyAuthority(PravaPristupa.ProfesorAddIspit.name())
                               .requestMatchers(HttpMethod.POST, "ispit/getPrijavljeni").hasAnyAuthority(PravaPristupa.ProfesorReadPrijavljeni.name())
                               .requestMatchers(HttpMethod.POST, "ispit/getNezakljuceniIspitniRok").hasAnyAuthority(PravaPristupa.ProfesorGetNeZakljuceniIspitniRok.name())
                               .requestMatchers(HttpMethod.POST, "ispit/getZakljucenIspitniRok").hasAnyAuthority(PravaPristupa.ProfesorGetZakljuceniIspitniRok.name())
                               .requestMatchers(HttpMethod.POST, "ispit/getZakljuceniForProfesor").hasAnyAuthority(PravaPristupa.ProfesorReadZakljuceniIspiti.name())
                               .requestMatchers(HttpMethod.POST, "ispit/setIsZakljucen").hasAnyAuthority(PravaPristupa.ProfesorSetIsZakljucen.name())
                               .requestMatchers(HttpMethod.GET, "obavestenje/getObavestenje").hasAnyAuthority(PravaPristupa.StudentReadObavestenja.name())
                               .requestMatchers(HttpMethod.GET, "obavestenje/getStaroObavestenje").hasAnyAuthority(PravaPristupa.StudentReadStaraObavestenja.name())
                               .requestMatchers(HttpMethod.GET, "predmet/getPredmetForStudent").hasAuthority(PravaPristupa.StudentReadPredmet.name())
                               .requestMatchers(HttpMethod.POST, "predmet/getAktivnost").hasAuthority(PravaPristupa.StudentReadAktivnost.name())
                               .requestMatchers(HttpMethod.POST, "predmet/getUpisani").hasAnyAuthority(PravaPristupa.ProfesorReadUpisani.name())
                               .requestMatchers(HttpMethod.GET, "predmet/getPredmetForProfesor").hasAnyAuthority(PravaPristupa.ProfesorReadPredmet.name())
                               .requestMatchers(HttpMethod.POST, "predmet/addAktivnost").hasAnyAuthority(PravaPristupa.ProfesorAddAktivnost.name())
                               .requestMatchers(HttpMethod.GET, "rata/getAllForStudent").hasAnyAuthority(PravaPristupa.StudentReadRata.name())
                               .requestMatchers(HttpMethod.POST, "rata/platiSkolarinu").hasAnyAuthority(PravaPristupa.StudentPlatiSkolarinu.name())
                               .requestMatchers(HttpMethod.GET, "student/getStudentById").hasAuthority(PravaPristupa.StudentReadData.name())
                               .requestMatchers(HttpMethod.GET, "student/getUpis").hasAnyAuthority(PravaPristupa.StudentReadUpis.name())
                               .requestMatchers(HttpMethod.GET, "student/getStanjeNaRacunu").hasAnyAuthority(PravaPristupa.StudentReadStanjeNaRacunu.name())
                               .requestMatchers(HttpMethod.POST, "student/updateStanjeNaRacunu").hasAnyAuthority(PravaPristupa.StudentUpdateStanjeNaRacunu.name())
                               .requestMatchers(HttpMethod.POST, "student/promenaSifreStudent").hasAnyAuthority(PravaPristupa.StudentPromeniSifru.name())
                               .requestMatchers(HttpMethod.GET, "profesor/getById").hasAnyAuthority(PravaPristupa.ProfesorReadData.name())
                               .requestMatchers(HttpMethod.GET, "profesor/promenaSifreProfesor").hasAnyAuthority(PravaPristupa.ProfesorPromeniSifru.name())
                               .anyRequest()
                               .authenticated()


               )
               .sessionManagement(
                       sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )
               .authenticationProvider(studentAuthenticationProvider)
               .authenticationProvider(profesorAuthenticationProvider)
               .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
               .logout(
                       l -> l.logoutUrl("/auth/logout")
                               .addLogoutHandler(logoutHandler)
                               .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
               )
               .cors(
                       c -> c.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
               );

       return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3005"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept", "Origin", "Content-Type", "Depth", "User-Agent", "If-Modified-Since,",
                "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With", "X-File-Name", "credentials")); // Dodajte "credentials" u dozvoljene headere

        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}