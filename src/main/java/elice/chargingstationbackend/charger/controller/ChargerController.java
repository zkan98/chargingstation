package elice.chargingstationbackend.charger.controller;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.charger.dto.ChargerListResponseDTO;
import elice.chargingstationbackend.charger.dto.ChargerRequestDTO;
import elice.chargingstationbackend.charger.dto.ChargerDetailResponseDTO;
import elice.chargingstationbackend.charger.dto.LocationDTO;
import elice.chargingstationbackend.charger.service.ChargerService;
import elice.chargingstationbackend.user.UserRepository;
import elice.chargingstationbackend.user.User;
import elice.chargingstationbackend.user.security.JwtUtil;
import elice.chargingstationbackend.user.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/charger")
public class ChargerController {

    private final ChargerService chargerService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    // 주변 충전소 리스트 자동 조회
    @PostMapping("/list")
    public ResponseEntity<List<ChargerListResponseDTO>> getNearbyChargerList(
        @RequestBody LocationDTO location,
        @RequestParam(required = false) List<String> chgerType,
        @RequestParam(required = false) List<String> output,
        @RequestParam(required = false) Double chargingFee,
        @RequestParam(required = false) String parkingFree,
        @RequestParam(required = false) List<String> kind,
        @RequestParam(required = false) List<Long> ownerIds
    ) {
        List<ChargerListResponseDTO> nearByChargerList = chargerService.getNearbyChargerList(location, chgerType, output, chargingFee, parkingFree, kind, ownerIds);
        return ResponseEntity.ok().body(nearByChargerList);
    }

    // 충전소 검색 (충전소 이름, 주소)
    @GetMapping("/search")
    public ResponseEntity<List<ChargerListResponseDTO>> searchCharger(@RequestParam String searchTerm) {
        List<ChargerListResponseDTO> resultList = chargerService.searchCharger(searchTerm);
        return ResponseEntity.ok().body(resultList);
    }

    // 충전소 세부 조회
    @GetMapping("/place/{statId}")
    public ResponseEntity<ChargerDetailResponseDTO> getChargerDetail(@PathVariable String statId) {
        ChargerDetailResponseDTO chargerDetail = chargerService.getChagerDetail(statId);
        return ResponseEntity.ok().body(chargerDetail);
    }

    // 사업자별 충전소 조회
    @GetMapping("/ownerList")
    public ResponseEntity<List<ChargerListResponseDTO>> getOwnerChargerList(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 제공되지 않았습니다.");
        }

        try {
            Claims claims = jwtUtil.extractToken(token);
            String email = claims.getSubject();  // 이메일 추출

            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효한 사용자 ID가 없습니다.");
            }

            User currentUser = user.get();
            if (!(currentUser instanceof BusinessOwner)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효한 사업자가 아닙니다.");
            }

            BusinessOwner businessOwner = (BusinessOwner) currentUser;
            Long ownerId = businessOwner.getId();

            // 충전소 목록 조회
            List<ChargerListResponseDTO> ownerChargerList = chargerService.getOwnerChargerList(ownerId);
            return ResponseEntity.ok(ownerChargerList);

        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 요청입니다.");
        }
    }

    // 충전소 추가
    @PostMapping("/place/addCharger")
    public ResponseEntity<String> addCharger(HttpServletRequest request, @RequestBody ChargerRequestDTO chargerRequestDTO) {
        String token = jwtUtil.extractTokenFromRequest(request);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 제공되지 않았습니다.");
        }

        try {
            Claims claims = jwtUtil.extractToken(token);
            String email = claims.getSubject();  // 이메일 추출

            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효한 사용자 ID가 없습니다.");
            }

            User currentUser = user.get();
            if (!(currentUser instanceof BusinessOwner)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효한 사업자가 아닙니다.");
            }

            BusinessOwner businessOwner = (BusinessOwner) currentUser;

            String statId = chargerService.addCharger(chargerRequestDTO, businessOwner);
            return ResponseEntity.ok("충전소가 성공적으로 추가되었습니다. 충전소 식별번호 : " + statId);

        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 요청입니다.");
        }
    }

    // 충전소 수정
    @PatchMapping("/place/updateCharger/{statId}")
    public ResponseEntity<String> updateCharger(@PathVariable String statId, @RequestBody ChargerRequestDTO chargerRequestDTO) {
        chargerService.updateCharger(statId, chargerRequestDTO);
        return ResponseEntity.ok("충전소 정보가 성공적으로 수정되었습니다. 충전소 식별번호 : " + statId);
    }

    // 충전소 삭제
    @DeleteMapping("/place/deleteCharger/{statId}")
    public ResponseEntity<String> deleteCharger(@PathVariable String statId) {
        chargerService.deleteCharger(statId);
        return ResponseEntity.ok("충전소 정보가 성공적으로 삭제되었습니다. 충전소 식별번호 : " + statId);
    }
}
