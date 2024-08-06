package elice.chargingstationbackend.charger.controller;

import elice.chargingstationbackend.charger.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/api")
    public String getChargerInfo(
            @RequestParam int pageNo,
            @RequestParam int numOfRows) throws IOException {
        return apiService.fetchChargerData(pageNo, numOfRows);
    }
}
