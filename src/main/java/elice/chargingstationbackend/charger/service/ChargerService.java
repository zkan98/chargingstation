package elice.chargingstationbackend.charger.service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

import elice.chargingstationbackend.charger.dto.LocationDTO;
import org.springframework.stereotype.Service;

import elice.chargingstationbackend.charger.dto.ChargerDetailResponseDTO;
import elice.chargingstationbackend.charger.dto.ChargerListResponseDTO;
import elice.chargingstationbackend.charger.dto.ChargerRequestDTO;
import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.charger.exception.ChargerNotFoundException;
import elice.chargingstationbackend.charger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargerService {
    private final ChargerRepository chargerRepository;

    // 주변 충전소 리스트 자동 조회
    public List<ChargerListResponseDTO> getNearbyChargerList(LocationDTO location) {
        Double userLatitude = location.getUserLatitude();
        Double userLongitude = location.getUserLongitude();

        List<Charger> nearByChargerList = chargerRepository.findCharger(userLatitude, userLongitude);

        return nearByChargerList.stream()
                                .map(ChargerListResponseDTO::new)
                                .collect(Collectors.toList());
    }
    
    // 충전소 검색(충전소 이름, 장소)
    public List<ChargerListResponseDTO> searchCharger(String searchTerm) {
        List<Charger> resultsList = chargerRepository.searchByChargerNameOrAddress(searchTerm);

        List<ChargerListResponseDTO> responseDTOList = resultsList.stream()
        .map(ChargerListResponseDTO::new)
        .collect(Collectors.toList());

        return responseDTOList;
    }

    // 충전소 세부 조회
    public ChargerDetailResponseDTO getChagerDetail(String statId) {
        Charger chargerDetail = chargerRepository.findById(statId)
                                        .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + statId));
        return new ChargerDetailResponseDTO(chargerDetail);
    }

    // 충전소 추가
    public String addCharger(ChargerRequestDTO chargerRequestDTO) {
        // BusinessOwner accessOwnerId = businessOwnerRepository.findById(ownerId)
        //                         .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        if (chargerRequestDTO.getStatId() == null || chargerRequestDTO.getStatId().isEmpty()) {
            chargerRequestDTO.setStatId(UUID.randomUUID().toString());
        }

        Charger newCharger = chargerRequestDTO.toEntity();
        chargerRepository.save(newCharger);

        return newCharger.getStatId();
    }

    // 충전소 수정
    public void updateCharger(String statId, ChargerRequestDTO chargerRequestDTO) {
        Charger chargerToUpdate = chargerRepository.findById(statId)
                                .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + statId));
        
        chargerToUpdate.setStatNm(chargerRequestDTO.getStatNm());
        chargerToUpdate.setAddr(chargerRequestDTO.getAddr());
        chargerToUpdate.setStat(chargerRequestDTO.getStat());
        chargerToUpdate.setSpeed(chargerRequestDTO.getSpeed());
        chargerToUpdate.setChargingFee(chargerRequestDTO.getChargingFee());
        chargerToUpdate.setPowerType(chargerRequestDTO.getPowerType());
        chargerToUpdate.setParkingFree(chargerRequestDTO.getParkingFree());

        chargerRepository.save(chargerToUpdate);
    }

    // 충전소 삭제
    public void deleteCharger(String statId) {
        Charger chargerToDelete = chargerRepository.findById(statId)
                                .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + statId));
        
        chargerRepository.delete(chargerToDelete);
    }
}
