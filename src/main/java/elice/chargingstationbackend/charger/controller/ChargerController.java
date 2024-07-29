package elice.chargingstationbackend.charger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elice.chargingstationbackend.charger.dto.ChargerDetailResponseDTO;
import elice.chargingstationbackend.charger.dto.ChargerListResponseDTO;
import elice.chargingstationbackend.charger.service.ChargerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class ChargerController {
    
    private final ChargerService chargerService;

    // 주변 충전소 리스트 자동 조회
    // @GetMapping
    // public ResponseEntity<Page<ChargerListResponseDTO>> getNearbyChargerList(@RequestBody Double latitude, @RequestBody Double longitute) {
    //     chargerService.getNearbyChargerList(latitude, longitute);
    // }

    // 충전소 세부 조회
    @GetMapping("/place/{chargerId}")
    public ResponseEntity<ChargerDetailResponseDTO> getChagerDetail(@PathVariable Long chargerId) {
        ChargerDetailResponseDTO chargerDetail = chargerService.getChagerDetail(chargerId);
        return ResponseEntity.ok().body(chargerDetail);
    }
}
