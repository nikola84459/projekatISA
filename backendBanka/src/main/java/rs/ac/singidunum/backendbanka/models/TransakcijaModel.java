package rs.ac.singidunum.backendbanka.models;

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

    @Column(name = "broj")
    private String broj;

    @Column(name = "datum_i_vreme")
    private LocalDateTime datumIVreme;

    @Column(name = "iznos")
    private double iznos;

    @ManyToOne
    @JoinColumn(name = "kartica_id")
    private KarticaModel kartica;
}
