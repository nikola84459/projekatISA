package rs.ac.singidunum.projekat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.singidunum.projekat.dtos.IspitDTO;
import rs.ac.singidunum.projekat.dtos.IspitPodaciDTO;
import rs.ac.singidunum.projekat.dtos.IspitPrijavljeniDTO;
import rs.ac.singidunum.projekat.models.*;
import rs.ac.singidunum.projekat.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IspitService implements IIspitService {
    @Autowired
    private IIspitRepository ispitRepository;

    @Autowired
    private IIspitniRokRepository ispitniRokRepository;

    @Autowired
    private IStudentPredmetRepository studentPredmetRepository;

    @Autowired
    private IIspitRasporedRepository iIspitRasporedRepository;

    @Autowired
    private IPredmetRepository iPredmetRepository;

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IIspitPrijavaRepository iIspitPrijavaRepository;

    @Autowired
    private StudentService studentService;


    @Autowired
    private IUpisRepository iUpisRepository;

    @Autowired
    private PredmetService predmetService;

    private IspitniRokModel ispitniRokModel;

    private PredmetModel predmetModel;


    @Override
    public List<IspitPrijavljeniDTO> getPrijavljeniForStudent(int studentId) {
        List<IspitPrijavaModel> ispitPrijava = iIspitPrijavaRepository.findByStudentPredmetStudentStudentIdAndIsZakljucen(studentId, false);

        if (ispitPrijava.isEmpty()) {
            return null;
        }

        List<IspitPrijavljeniDTO> prijavljeni = ispitPrijava.stream()
                .map(i -> {
                    IspitPrijavljeniDTO ispitPrijavljeniDTO = new IspitPrijavljeniDTO();
                    ispitPrijavljeniDTO.setPredmet(i.getStudentPredmet().getPredmet());
                    ispitPrijavljeniDTO.setIspitniRok(i.getIspitniRok());
                    ispitPrijavljeniDTO.setIspit(i.getIspit());
                    return ispitPrijavljeniDTO;
                }).collect(Collectors.toList());

        return prijavljeni;
    }

    @Override
    public List<IspitniRokModel> getIspitniRok() {
        List<IspitniRokModel> ispitniRok = ispitniRokRepository.findByPocetakPrijaveLessThanEqualAndKrajGreaterThanEqual(LocalDate.now(), LocalDate.now());

        if (ispitniRok.isEmpty()) {
            return null;
        }

        return ispitniRok;
    }

    @Override
    public List<IspitPrijavaModel> getZakljuceniForStudent(int studentId, boolean isPolozen) {
        List<IspitPrijavaModel> zakljuceni = iIspitPrijavaRepository.findByStudentPredmetStudentStudentIdAndIsPolozenAndIsZakljucen(studentId, isPolozen, true);

        if (zakljuceni.isEmpty()) {
            return null;
        }

        return zakljuceni;
    }

    @Override
    public IspitRaspored getIspitRaspored(int ispitniRokId, int predmetId) {
        return iIspitRasporedRepository.getIspitRasporedByIspitniRokIspitniRokIdAndPredmetPredmetId(ispitniRokId, predmetId);
    }

    @Override
    public List<IspitniRokModel> getByIsZakljucen(int predmetId, boolean isZakljucen) {
        List<IspitniRokModel> ispitniRok = iIspitPrijavaRepository.findByisZakljuceniIspitPrijava(predmetId, isZakljucen);

        if(ispitniRok.isEmpty()) {
            return null;
        }

        return ispitniRok;
    }

    @Override
    public boolean isAktivnaPrijavaByIspitniRok(int ispitniRokId) {
        return ispitniRokRepository.existsByPocetakPrijaveLessThanEqualAndKrajGreaterThanEqualAndIspitniRokId(LocalDate.now(), LocalDate.now(), ispitniRokId);
    }

    @Override
    public boolean isPrijavljen(int studentId, int ispitniRokId, int predmetId) {
        return iIspitPrijavaRepository.existsByStudentPredmetStudentStudentIdAndIspitniRokIspitniRokIdAndStudentPredmetPredmetPredmetIdAndIsZakljucen(studentId, ispitniRokId, predmetId, false);
    }

    @Override
    public IspitDTO ispitDetalji(int ispitniRokId, int predmetId) {
        IspitRaspored ispitRaspored = iIspitRasporedRepository.getIspitRasporedByIspitniRokIspitniRokIdAndPredmetPredmetId(ispitniRokId, predmetId);

        if(ispitRaspored == null) {
            return null;
        }

        long dani = ChronoUnit.DAYS.between(LocalDateTime.now(), ispitRaspored.getDatumIVreme());


        this.ispitniRokModel = ispitRaspored.getIspitniRok();
        this.predmetModel = ispitRaspored.getPredmet();

        IspitDTO ispitDTO = IspitDTO.builder()
                        .ispitniRokId(ispitniRokId)
                        .predmetNaziv(ispitRaspored.getPredmet().getNaziv())
                        .ispitniRokNaziv(ispitRaspored.getIspitniRok().getNaziv())
                        .vremeIspita(ispitRaspored.getDatumIVreme())
                        .build();

        if (dani >= 5) {
            ispitDTO.setCenaPrijave(ispitRaspored.getIspitniRok().getCenaRedovnePrijave());
            ispitDTO.setRedovnaPrijava(true);
        } else {
            ispitDTO.setCenaPrijave(ispitRaspored.getIspitniRok().getCenaVanrednePrijave());
            ispitDTO.setRedovnaPrijava(false);
        }

        return ispitDTO;
    }

    @Override
    public List<IspitniRokModel> getZakljucen(int predmetId) {
        List<IspitniRokModel> ispitniRok = ispitniRokRepository.findByIspitPrijavaStudentPredmetPredmetPredmetIdAndIspitPrijavaIsZakljucen(predmetId, false);

        if (ispitniRok.isEmpty()) {
            return null;
        }

        return ispitniRok;
    }

    @Override
    @Transactional
    public IspitPrijavaModel prijavaIspita(int studentId, int ispitniRokId, int predmetId) {
        StudentModel student = studentService.getById(studentId);
        IspitDTO ispitDTO = ispitDetalji(ispitniRokId, predmetId);

        if (!isAktivnaPrijavaByIspitniRok(ispitniRokId)) {
            throw new IllegalArgumentException("Za ispitni rok za koji pokušavate da prijavite ispit nije aktivna prijava.");

        }

        if (ispitDTO.getCenaPrijave() > student.getStanjeNaRacunu()) {
            throw new IllegalArgumentException("Nemate dvoljno novca na računu kako bi ste izvršili prijavu ispita.");
        }

        double novoStanje = student.getStanjeNaRacunu() - ispitDTO.getCenaPrijave();

        LocalDate datumIspita = ispitDTO.getVremeIspita().toLocalDate();

        if (datumIspita == LocalDate.now()) {
            throw new IllegalArgumentException("Nije moguće prijaviti ispit na dan održavanja ispita.");
        }

        if (datumIspita.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ispit koji pokušavate da prijavite je prošao.");
        }

        StudentPredmet studentPredmet = predmetService.getPredmetForStudent(studentId, predmetId);

        if (studentPredmet == null) {
            throw new IllegalArgumentException("Niste upisani na predmet koji pokušavate da prijavite.");
        }

        if (isPrijavljen(studentId, ispitniRokId, predmetId)) {
            throw new IllegalArgumentException("Ispit koji pokušavate da prijavite je već prijavljen u izabranom ispitnom roku.");
        }

        return addIspitPrijava(student, studentPredmet, novoStanje);

    }

    private IspitPrijavaModel addIspitPrijava(StudentModel studentModel, StudentPredmet studentPredmet, double novoStanje) {
        StudentModel student = studentModel;
        student.setStanjeNaRacunu(novoStanje);
        studentRepository.save(student);

        IspitPrijavaModel ispitPrijava = IspitPrijavaModel.builder()
                        .ispitniRok(this.ispitniRokModel)
                        .studentPredmet(studentPredmet)
                        .isZakljucen(false)
                        .build();

        return iIspitPrijavaRepository.save(ispitPrijava);


    }

    private boolean isValidPredmetProfesor(int predmetId, int profesorId) {
        return iPredmetRepository.existsByPredmetIdAndProfesorProfesorId(predmetId, profesorId);
    }


    @Override
    @Transactional
    public boolean addIspit(List<IspitPodaciDTO> ispitPrijavaId, int profesorId) {
      for (IspitPodaciDTO podatak : ispitPrijavaId) {
            IspitPrijavaModel ispitPrijava = getIspitPrijavaById(podatak.getIspitPrijavaId());

            if (ispitPrijava == null || !isValidPredmetProfesor(ispitPrijava.getStudentPredmet().getPredmet().getPredmetId(), profesorId)) {
                return false;
            }

            StudentPredmet predmet = predmetService.addAktivnostIspit(ispitPrijava.getStudentPredmet(), podatak.getAktivnost(), podatak.getPrviKolokvijum(), podatak.getDrugiKolokvijum());

            if (predmet == null) {
                return false;
            }

            IspitModel im = ispitRepository.findByIspitPrijavaIspitPrijavaId(ispitPrijava.getIspitPrijavaId());

            if(podatak.getIspitOcena() != null) {
                updateIsPolozen(ispitPrijava, podatak.getIspitOcena());
            }
            
            Integer bodoviUkupno = calculateBodovi(podatak,im, ispitPrijava);

            if (im == null) {
                return insertIspit(podatak, ispitPrijava, bodoviUkupno);

            } else {
                updateIspit(podatak, im, bodoviUkupno);
                return true;
            }
        }

        return false;
    }

    private boolean insertIspit(IspitPodaciDTO podatak, IspitPrijavaModel ispitPrijava, Integer bodoviUkupno) {
        List<IspitModel> ispit = new ArrayList<>();

        if(podatak.getIspitOcena() == null) {
            throw new IllegalArgumentException("Ocena je obavezna podatak");
        }

        if(podatak.getIspitOcena() >= 5 && podatak.getIspitOcena() <= 10) {
            IspitModel i = IspitModel.builder()
                    .bodovi(podatak.getIspitBodovi() != null ? podatak.getIspitBodovi() : null)
                    .bodoviUkupno(bodoviUkupno)
                    .ocena(podatak.getIspitOcena())
                    .isIzasao(podatak.getIspitOcena() != 0)
                    .ispitPrijava(ispitPrijava)
                    .build();

            ispit.add(i);
        } else {
            return false;
        }

        ispitRepository.saveAll(ispit);
        return true;
    }

    private void updateIspit(IspitPodaciDTO podatak, IspitModel im, Integer bodoviUkupno) {
        im.setBodovi(podatak.getIspitBodovi() != null ? podatak.getIspitBodovi() : im.getBodovi());
        im.setBodoviUkupno(bodoviUkupno);
        im.setOcena(
                (podatak.getIspitOcena() != null && podatak.getIspitOcena() >= 5 && podatak.getIspitOcena() <= 10)
                        ? podatak.getIspitOcena()
                        : im.getOcena()
        );

        im.setIzasao(podatak.getIspitOcena() != null ? podatak.getIspitOcena() != 0 : im.isIzasao());

        ispitRepository.save(im);
    }

    private Integer calculateBodovi(IspitPodaciDTO podatak, IspitModel im, IspitPrijavaModel ispitPrijava) {
        Integer bodoviUkupno = null;

        Integer ispitBodovi = Optional.ofNullable(podatak.getIspitBodovi()).orElse(im != null ? im.getBodovi() : null);
        Integer aktivnost = podatak.getAktivnost() != null ? podatak.getAktivnost() : ispitPrijava.getStudentPredmet().getAktivnost();
        Integer prviKolokvijum = podatak.getPrviKolokvijum() != null ? podatak.getPrviKolokvijum() : ispitPrijava.getStudentPredmet().getPrviKolokvijum();
        Integer drugiKolokvijum = podatak.getDrugiKolokvijum() != null ? podatak.getDrugiKolokvijum() : ispitPrijava.getStudentPredmet().getDrugiKolokvijum();

        if(ispitBodovi != null) {
            if(bodoviUkupno == null) {
                bodoviUkupno = 0;
            }
            bodoviUkupno += ispitBodovi;
        }

        if(aktivnost != null) {
            if(bodoviUkupno == null) {
                bodoviUkupno = 0;
            }
            bodoviUkupno += aktivnost;
        }

        if(prviKolokvijum != null) {
            if(bodoviUkupno == null) {
                bodoviUkupno = 0;
            }
            bodoviUkupno += prviKolokvijum;
        }

        if(drugiKolokvijum != null) {
            if(bodoviUkupno == null) {
                bodoviUkupno = 0;
            }
            bodoviUkupno += drugiKolokvijum;
        }

        return bodoviUkupno;
    }

    private void updateIsPolozen(IspitPrijavaModel ispitPrijava, int ocena) {
        if(ocena > 5 && ocena <= 10) {
            ispitPrijava.setPolozen(true);
            iIspitPrijavaRepository.save(ispitPrijava);
        } else {
            if(ispitPrijava.isPolozen()) {
                ispitPrijava.setPolozen(false);
                iIspitPrijavaRepository.save(ispitPrijava);
            }
        }
    }

    @Override
    public List<IspitPrijavaModel> getPrijavljeni(int predmetId, int ispitniRokId) {
        List<IspitPrijavaModel> prijavljeni = iIspitPrijavaRepository.findByStudentPredmetPredmetPredmetIdAndIspitniRokIspitniRokIdAndIsZakljucen(predmetId, ispitniRokId, false);

        if (prijavljeni.isEmpty()) {
            return null;
        }

        return prijavljeni;
    }

    @Override
    public List<IspitPrijavaModel> getZakljuceniForProfesor(int predmetId, int ispitniRokId) {
        List<IspitPrijavaModel> zakljuceni = iIspitPrijavaRepository.findByStudentPredmetPredmetPredmetIdAndIspitniRokIspitniRokIdAndIsZakljucen(predmetId, ispitniRokId, true);

        if(zakljuceni.isEmpty()) {
            return null;
        }

        return zakljuceni;
    }

    @Override
    public IspitPrijavaModel getIspitPrijavaById(int ispitPrijavaId) {
        IspitPrijavaModel ispitPrijava = iIspitPrijavaRepository.findById(ispitPrijavaId).orElse(null);

        if(ispitPrijava == null) {
            return null;
        }

        return ispitPrijava;
    }

    @Override
    @Transactional
    public boolean setIsZakljucen(List<Integer> ispId, int profesorId) {
        for (Integer i : ispId) {
            IspitPrijavaModel ispitPrijava = getIspitPrijavaById(i);

            if (ispitPrijava == null) {
                return false;
            }

            if (ispitPrijava.isPolozen()) {
                ispitPrijava.getStudentPredmet().setAktivan(false);
                studentPredmetRepository.save(ispitPrijava.getStudentPredmet());
            }

            if (!iPredmetRepository.existsByPredmetIdAndProfesorProfesorId(ispitPrijava.getStudentPredmet().getPredmet().getPredmetId(), profesorId)) {
                return false;
            }

            UpisModel upis = iUpisRepository.findByStudentStudentIdAndIsAktivan(ispitPrijava.getStudentPredmet().getStudent().getStudentId(), true);

            if (upis == null) {
                return false;
            }

            ispitPrijava.setZakljucen(true);
            iIspitPrijavaRepository.save(ispitPrijava);

            upis.setOstvarenoEspb(upis.getOstvarenoEspb() + ispitPrijava.getStudentPredmet().getPredmet().getEspb());
            iUpisRepository.save(upis);
        }

        return true;
   }
}