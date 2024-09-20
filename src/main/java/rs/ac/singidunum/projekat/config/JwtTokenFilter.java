package rs.ac.singidunum.projekat.config;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.repositories.IProfesorTokenRepository;
import rs.ac.singidunum.projekat.repositories.IStudentTokenRepository;
import rs.ac.singidunum.projekat.services.JwtService;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService studentDetailsService;
    private final UserDetailsService profesorDetailsService;
    private final IStudentTokenRepository studentTokenRepository;
    private final IProfesorTokenRepository profesorTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String requestHeader = request.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwt = requestHeader.substring(7);
            username = jwtService.extractUserName(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = null;
                boolean isTokenValid = false;
                if (jwtService.extractUserRole(jwt).equals("Student")) {
                    userDetails = this.studentDetailsService.loadUserByUsername(username);
                    isTokenValid = studentTokenRepository.findByToken(jwt)
                            .map(t -> !t.isIstekao() && !t.isNevazeci())
                            .orElse(false);
                } else if (jwtService.extractUserRole(jwt).equals("Profesor")) {
                    userDetails = this.profesorDetailsService.loadUserByUsername(username);
                    isTokenValid = profesorTokenRepository.findByToken(jwt)
                            .map(t -> !t.isIstekao() && !t.isNevazeci())
                            .orElse(false);
                }

                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}