package rs.ac.singidunum.projekat.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.singidunum.projekat.enums.RoleEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    @JsonProperty("accessToken")
    private String authToken;
    private String refreshToken;
    private String uloga;

}
