package rs.ac.singidunum.backendplacanje.services;

import rs.ac.singidunum.backendplacanje.models.BankaModel;
import rs.ac.singidunum.backendplacanje.models.TransakcijaModel;

import java.time.LocalDateTime;

public interface ITransakcijaService {
    TransakcijaModel addTransakcija(String brojTransakcije, String brojKartice, double iznos, boolean isUspesna, BankaModel banka, String bankaBrTransakcije);
}
