package rs.ac.singidunum.projekat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.singidunum.projekat.enums.PravaPristupa;
import rs.ac.singidunum.projekat.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "student")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StudentModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "br_indeksa")
    private String brIndeksa;

    @Column(name = "jmbg")
    private String jmbg;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "stanje_na_racunu")
    private double stanjeNaRacunu;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<UpisModel> upis;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_prava_pristupa",
            joinColumns = @JoinColumn(name = "student_id"),
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
        return brIndeksa;
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