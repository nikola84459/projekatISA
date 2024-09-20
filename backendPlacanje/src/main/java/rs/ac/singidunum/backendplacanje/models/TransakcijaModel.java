package rs.ac.singidunum.backendplacanje.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transakcija")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransakcijaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transakcijaId;

    @Column(name = "broj_transakcije")
    private String brojTransakcije;

    @Column(name = "broj_kartice")
    private String brojKartice;

    @Column(name = "datum_i_vreme")
    private LocalDateTime datumIVreme;

    @Column(name = "iznos")
    private double iznos;

    @Column(name = "is_uspesna")
    private boolean isUspesna;

    @Column(name = "banka_br_transakcije")
    private String bankaBrTransakcije;

    @ManyToOne
    @JoinColumn(name = "banka_id")
    private BankaModel banka;
}
