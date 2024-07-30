package elice.chargingstationbackend.maps.controller;


import elice.chargingstationbackend.maps.mapsDto.ChargeStationDTO;
import elice.chargingstationbackend.maps.mapsDto.CoordinateDTO;
import elice.chargingstationbackend.maps.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MapController {
    private final MapService mapService;

    @Autowired
    MapController(MapService mapService){
        this.mapService = mapService;
    }

    @PostMapping("/coordinate")
    public ResponseEntity<List<ChargeStationDTO>> findChargestation(@RequestBody CoordinateDTO coordinateDTO){

        double lat = coordinateDTO.getLat();
        double lng = coordinateDTO.getLng();
        double distance = coordinateDTO.getDistance();

        List<ChargeStationDTO> chargeStationDTOList =  mapService.getChargeStationsWithinDistance(lat, lng, distance);

        return ResponseEntity.ok(chargeStationDTOList);
    }

}
