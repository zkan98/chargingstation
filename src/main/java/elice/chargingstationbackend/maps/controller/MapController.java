package elice.chargingstationbackend.maps.controller;


import elice.chargingstationbackend.maps.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {
    private final MapService mapService;

    @Autowired
    MapController(MapService mapService){
        this.mapService = mapService;
    }

    @PostMapping("/")
    public ResponseEntity<>

}
