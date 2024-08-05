package elice.chargingstationbackend.charger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Double userLatitude;
    private Double userLongitude;
}
