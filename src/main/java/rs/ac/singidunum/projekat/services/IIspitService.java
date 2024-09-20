package rs.ac.singidunum.projekat.services;

import rs.ac.singidunum.projekat.dtos.IspitDTO;
import rs.ac.singidunum.projekat.dtos.IspitPodaciDTO;
import rs.ac.singidunum.projekat.dtos.IspitPrijavaDTO;
import rs.ac.singidunum.projekat.dtos.IspitPrijavljeniDTO;
import rs.ac.singidunum.projekat.models.*;

import java.util.List;

public interface IIspitService {

  List<IspitPrijavljeniDTO> getPrijavljeniForStudent(int studentId);

 List<IspitniRokModel> getIspitniRok();

 List<IspitPrijavaModel> getZakljuceniForStudent(int studentId, boolean isPolozen);

 IspitRaspored getIspitRaspored(int ispitniRokId, int predmetId);

    IspitDTO ispitDetalji(int ispitniRokId, int predmetId);


    IspitPrijavaModel prijavaIspita(int studentId, int ispitniRokId, int predmetId);

    List<IspitniRokModel> getByIsZakljucen(int predmetId, boolean isZakljucen);

    boolean isAktivnaPrijavaByIspitniRok(int ispitniRokId);

    boolean isPrijavljen(int studentId, int ispitniRokId, int predmetId);

    List<IspitniRokModel> getZakljucen(int predmetId);

    boolean addIspit(List<IspitPodaciDTO> ispitPodaci, int profesorId);

    List<IspitPrijavaModel> getPrijavljeni(int predmetId, int ispitniRokId);

    List<IspitPrijavaModel> getZakljuceniForProfesor(int predmetId, int ispitniRokId);

    IspitPrijavaModel getIspitPrijavaById(int ispitPrijavaId);

    boolean setIsZakljucen(List<Integer> ispId, int profesorId);

}
