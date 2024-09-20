package rs.ac.singidunum.backendbanka.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransakcijaDTO {
    private boolean isUspesno;
    private String poruka;
    private String brojTransakcije;
}
