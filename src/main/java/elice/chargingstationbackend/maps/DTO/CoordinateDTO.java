package elice.chargingstationbackend.maps.mapsDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateDTO {
    private double lat;
    private double lng;
    private double distance = 5.0;
}
