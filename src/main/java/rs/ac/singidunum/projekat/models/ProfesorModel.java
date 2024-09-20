package rs.ac.singidunum.projekat.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.singidunum.projekat.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "profesor")
@Data
public class ProfesorModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profesorId;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "kime")
    private String kime;

    @Column(name = "email")
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profesor_prava_pristupa",
            joinColumns = @JoinColumn(name = "profesor_id"),
            inverseJoinColumns = @JoinColumn(name = "prava_pristupa_id")
    )
    private List<PravaPristupaModel> pravaPristupa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return pravaPristupa.stream()
                .map(prava -> new SimpleGrantedAuthority(prava.getNaziv()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return kime;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
