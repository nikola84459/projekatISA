package rs.ac.singidunum.projekat.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.enums.RoleEnum;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import static rs.ac.singidunum.projekat.constants.ConfigConstants.*;

@Service
public class JwtService {
    private <T> T extractClaims(String token, Function<Claims, T> claimsReslover) {
        final Claims claims = extractAllClaims(token);
        return claimsReslover.apply(claims);
    }

    private Date extractExparation(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails, RoleEnum role) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("role", role);
        return buildToken(claim, userDetails, JWT_EXPIRATION);
    }

    public boolean isTokenExpired(String token) {
        return extractExparation(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUserRole(String token) {
        return extractClaims(token, claims -> (String) claims.get("role"));
    }

    public String extractUserName(String token) {
       return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);

        try {
            if (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
                return true;
            }
        } catch (ExpiredJwtException ex) {
            System.out.println("Token istekao: " + ex.getMessage());
        }

        return false;
    }

    private Key getSignInKey() {
        byte[] byteKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteKey);
    }

    public String generateRefreshToken(UserDetails userDetails, RoleEnum role) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("role", role);
        return buildToken(claim, userDetails, REFRESH_EXPIRATION);
    }

    private String buildToken(HashMap<String, Object> claims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}