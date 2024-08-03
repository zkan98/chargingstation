package elice.chargingstationbackend.maps.controller;


import elice.chargingstationbackend.maps.DTO.ChargeStationDTO;
import elice.chargingstationbackend.maps.DTO.CoordinateDTO;
import elice.chargingstationbackend.maps.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/charge-stations")
public class MapController {
    private final MapService mapService;

    @Autowired
    MapController(MapService mapService){
        this.mapService = mapService;
    }

    //지역구분코드(zcode)를 클라이언트 쪽에서 도로명을 통해 번호로 바꾸기.
    @PostMapping("/getChargerInfo")
    public ResponseEntity<List<ChargeStationDTO>> findChargestation(@RequestBody CoordinateDTO coordinateDTO) throws IOException {

        double lat = coordinateDTO.getLat();
        double lng = coordinateDTO.getLng();
        double distance = 55.0;
        String zcode = coordinateDTO.getZcode();

        List<ChargeStationDTO> chargeStationDTOList =  mapService.getChargeStationsWithinDistance(lat, lng, distance);

        return ResponseEntity.ok(chargeStationDTOList);
    }

}
