package rs.ac.singidunum.projekat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.projekat.dtos.RataDTO;
import rs.ac.singidunum.projekat.models.RacunModel;
import rs.ac.singidunum.projekat.models.RataModel;
import rs.ac.singidunum.projekat.models.StudentModel;
import rs.ac.singidunum.projekat.models.UpisModel;
import rs.ac.singidunum.projekat.repositories.*;
import rs.ac.singidunum.projekat.response.PlacanjeResponse;

import java.time.LocalDate;
import java.util.List;

@Service
public class RataService implements IRataService{

    @Autowired
    private IRataRepository iRataRepository;

    @Autowired
    private PlacanjeResponse placanje;

    @Autowired
    private IPlacanjeService iPlacanjeService;

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IRacunRepository racunRepository;

    @Autowired
    private IUpisRepository upisRepository;


    @Override
    public RataDTO getAllForStudent(int studentId) {
        List<RataModel> rata = iRataRepository.findByStudentStudentIdOrderByRokPlacanjaAsc(studentId);

        if(rata.isEmpty()) {
            return null;
        }


        double dug = rata.stream()
                .mapToDouble(r -> {
                    double dugovanje = 0;
                    if(!r.isPlaceno()) {
                        dugovanje = r.getIznos();
                    }

                    return dugovanje;
                }).sum();

        double dugNaDanašnjiDan = rata.stream().
                mapToDouble(r -> {
                    double trenutniDug = 0;
                    if(r.getRokPlacanja().isBefore(LocalDate.now()) && !r.isPlaceno()) {
                        trenutniDug = r.getIznos();
                    }

                    return trenutniDug;
                }).sum();

        double ukupnoUplaceno = rata.stream().
                mapToDouble(r -> {
                    double ukupno = 0;
                    if(r.isPlaceno()) {
                        ukupno = r.getIznos();

                    }

                    return ukupno;
            }).sum();

        RataDTO rataDTO = RataDTO.builder()
                        .rata(rata)
                        .ukupnoDugovanje(dug)
                        .dugovanjeNaDanasnjiDan(dugNaDanašnjiDan)
                        .ukupnoUplaceno(ukupnoUplaceno)
                        .build();

        return rataDTO;
    }

    @Override
    public List<RataModel> getRataForPlacanje(List<Integer> rataIds) {
        List<RataModel> rata = iRataRepository.findAllById(rataIds);

        if(rata.isEmpty()) {
            return null;
        }

        return rata;
    }

    @Override
    public RacunModel getRacun(int smerId) {
        RacunModel racun = racunRepository.findBySmerSmerId(smerId);

        if(racun == null) {
            return null;
        }

        return racun;
    }

    @Override
    public PlacanjeResponse platiSkolarinu(String brojKartice, String datumIsteka, String cvv, List<Integer> rataIds, int studentId) {
        List<RataModel> rata = getRataForPlacanje(rataIds);
        StudentModel student = studentRepository.findById(studentId).orElse(null);

        if (rata == null) {
            return null;
        }

        if(student == null) {
            return null;
        }

        UpisModel upis = upisRepository.findByStudentStudentIdAndIsAktivan(studentId, true);

        for (RataModel r : rata) {
            if (r.isPlaceno()) {
                placanje.setUspesno(false);
                placanje.setPoruka("Nije moguće platiti već plaćene rate.");
                return null;

            }

            if(r.getStudent().getStudentId() != student.getStudentId()) {
                placanje.setUspesno(false);
                placanje.setPoruka("Nije moguće izvršiti plaćanje.");
                return null;
            }
        }

        RacunModel racun = getRacun(upis.getSmer().getSmerId());

        if(racun == null) {
            return null;
        }

        String racunBr = racun.getBroj();


        double iznos = rata.stream()
                .mapToDouble(RataModel::getIznos).sum();

        PlacanjeResponse placanje = iPlacanjeService.bankaKonekcija(brojKartice, datumIsteka, cvv, iznos, racunBr);

        if (placanje.isUspesno()) {
            for (RataModel r : rata) {
                System.out.println(r.getRataId());
                r.setPlaceno(true);
                iRataRepository.save(r);
            }
        }

        return placanje;
    }
}