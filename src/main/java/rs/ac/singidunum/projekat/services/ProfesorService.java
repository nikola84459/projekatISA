package rs.ac.singidunum.projekat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.models.ProfesorModel;
import rs.ac.singidunum.projekat.repositories.IProfesorRepository;

@Service
public class ProfesorService implements IProfesorService {

    @Autowired
    private IProfesorRepository iProfesorRepository;

    @Override
    public ProfesorModel getProfesorById(int profesorId) {
        ProfesorModel profesor = iProfesorRepository.findById(profesorId).orElse(null);

        if(profesor == null) {
            return null;
        }

        return profesor;
    }

    @Override
    public ProfesorModel getProfesorByKime(String kime) {
        ProfesorModel profesor = iProfesorRepository.findByKime(kime);
        return profesor;
    }


    @Override
    public int getPrijavljen() {
        int profesorId = 0;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userDetails instanceof ProfesorModel) {
            profesorId = ((ProfesorModel) userDetails).getProfesorId();
        }

        return profesorId;
    }

    @Override
    public boolean promenaSifre(PasswordEncoder passwordEncoder, String staraSifra, String sifra, String ponovoNovaSifra) {
        int studentId = getPrijavljen();

        ProfesorModel profesor = iProfesorRepository.findById(studentId).orElse(null);

        if(profesor == null) {
            return false;
        }

        if(!passwordEncoder.matches(staraSifra, profesor.getPassword())) {
            throw new IllegalArgumentException("Uneta stara šifra nije ispravna");
        }

        if(!sifra.equals(ponovoNovaSifra)) {
            throw new IllegalArgumentException("Šifre se ne poklapaju.");
        }

        profesor.setPasswordHash(passwordEncoder.encode(sifra));
        iProfesorRepository.save(profesor);

        return true;
    }
}
