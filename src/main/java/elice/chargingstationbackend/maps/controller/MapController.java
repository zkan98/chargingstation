package elice.chargingstationbackend.maps.controller;


import elice.chargingstationbackend.maps.mapsDto.ChargeStationDTO;
import elice.chargingstationbackend.maps.mapsDto.CoordinateDTO;
import elice.chargingstationbackend.maps.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/charge-stations")
public class MapController {
    private final MapService mapService;

    @Autowired
    MapController(MapService mapService){
        this.mapService = mapService;
    }

    @GetMapping("/api")
    public ResponseEntity<String> getChargerInfoByApi(@RequestParam int pageNo, @RequestParam int numOfRows,
                                                      @RequestParam String zcode){
        try {
            String response = mapService.getChargestationApi(pageNo, numOfRows, zcode);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }


    @PostMapping("/getChargerInfo")
    public ResponseEntity<List<ChargeStationDTO>> findChargestation(@RequestBody CoordinateDTO coordinateDTO){

        double lat = coordinateDTO.getLat();
        double lng = coordinateDTO.getLng();
        double distance = coordinateDTO.getDistance();

        List<ChargeStationDTO> chargeStationDTOList =  mapService.getChargeStationsWithinDistance(lat, lng, distance);

        return ResponseEntity.ok(chargeStationDTOList);
    }

}
