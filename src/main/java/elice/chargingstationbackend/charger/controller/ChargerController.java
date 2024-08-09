package elice.chargingstationbackend.charger.controller;

import java.util.List;

import elice.chargingstationbackend.charger.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import elice.chargingstationbackend.charger.service.ChargerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/charger")
public class ChargerController {
    
    private final ChargerService chargerService;
    
    // 주변 충전소 리스트 자동 조회
    @PostMapping("/list")
    public ResponseEntity<List<ChargerListResponseDTO>> getNearbyChargerList(@RequestBody LocationDTO location,
                                                                             @RequestBody(required = false) ChargerFilterDTO filter) {
        if (filter == null) {
            filter = new ChargerFilterDTO(); // 빈 필터 객체로 초기화
        }

        List<ChargerListResponseDTO> nearByChargerList = chargerService.getNearbyChargerList(location, filter);

        return ResponseEntity.ok().body(nearByChargerList);
    }

    // 충전소 검색(충전소 이름, 주소)
    @GetMapping("/search")
    public ResponseEntity<List<ChargerListResponseDTO>> searchCharger(@RequestParam String searchTerm) {
        List<ChargerListResponseDTO> resultList = chargerService.searchCharger(searchTerm);
        
        return ResponseEntity.ok().body(resultList);
    }
    
    // 충전소 세부 조회
    @GetMapping("/place/{statId}")
    public ResponseEntity<ChargerDetailResponseDTO> getChagerDetail(@PathVariable String statId) {
        ChargerDetailResponseDTO chargerDetail = chargerService.getChagerDetail(statId);
        
        return ResponseEntity.ok().body(chargerDetail);
    }

    // 충전소 추가
    @PostMapping("/place/addCharger")
    // @PreAuthorize("hasAuthority('')")
    public ResponseEntity<String> addCharger(@RequestBody ChargerRequestDTO chargerRequestDTO) {
        // if(!jwtUtil.validateToken(accessToken)) {
        //     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 요청입니다.");
        // }
        
        // Long ownerId = jwtUtil.getMemberIdFromToken(accessToken);

        String statId = chargerService.addCharger(chargerRequestDTO);

        return ResponseEntity.ok("충전소가 성공적으로 추가되었습니다. 충전소 식별번호 : " + statId);
    }

    // 충전소 수정
    @PatchMapping("/place/updateCharger/{statId}")
    // @PreAuthorize("hasAuthority('')")
    public ResponseEntity<String> updateCharger(@PathVariable String statId, @RequestBody ChargerRequestDTO chargerRequestDTO) {
        // if(!jwtUtil.validateToken(accessToken)) {
        //     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 요청입니다.");
        // }
        
        // Long ownerId = jwtUtil.getMemberIdFromToken(accessToken);
        
        chargerService.updateCharger(statId, chargerRequestDTO);

        return ResponseEntity.ok("충전소 정보가 성공적으로 수정되었습니다. 충전소 식별번호 : " + statId);
    }

    // 충전소 삭제
    @DeleteMapping("/place/deleteCharger/{statId}")
    // @PreAuthorize("hasAuthority('')")
    public ResponseEntity<String> deleteCharger(@PathVariable String statId) {
        // if(!jwtUtil.validateToken(accessToken)) {
        //     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 요청입니다.");
        // }
        
        // Long ownerId = jwtUtil.getMemberIdFromToken(accessToken);

        chargerService.deleteCharger(statId);

        return ResponseEntity.ok("충전소 정보가 성공적으로 삭제되었습니다. 충전소 식별번호 : " + statId);
    }
}
