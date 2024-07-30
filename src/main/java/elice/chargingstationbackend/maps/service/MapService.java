package elice.chargingstationbackend.maps.service;

import elice.chargingstationbackend.maps.Entity.ChargeStationAPI;
import elice.chargingstationbackend.maps.mapsDto.ChargeStationDTO;
import elice.chargingstationbackend.maps.repository.ChargeStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {

    private final ChargeStationRepository chargeStationRepository;

    @Autowired
    public MapService(ChargeStationRepository chargeStationRepository) {
        this.chargeStationRepository = chargeStationRepository;
    }

    public List<ChargeStationDTO> getChargeStationsWithinDistance(Double lat, Double lng, double distance){

        double latRange = distance / 111.0;
        double lngRange = distance / (111.0 * Math.cos(Math.toRadians(lat)));

        double minLat = lat - latRange;
        double maxLat = lat + latRange;
        double minLng = lng - lngRange;
        double maxLng = lng + lngRange;

        List<ChargeStationAPI> chargeStations = chargeStationRepository.findWithinApproximateRange(minLat, maxLat, minLng, maxLng);

        return chargeStations.stream().
    }
}
