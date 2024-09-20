package rs.ac.singidunum.projekat.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    @NotNull
    @Size(min = 1, max = 10)
    private String username;

    @NotNull
    @Size(min = 1, max = 100)
    private String password;
}
