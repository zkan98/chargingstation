package elice.chargingstationbackend.charger.service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.exception.BusinessOwnerNotFoundException;
import elice.chargingstationbackend.business.repository.BusinessOwnerRepository;
import elice.chargingstationbackend.charger.dto.*;
import org.springframework.stereotype.Service;

import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.charger.exception.ChargerNotFoundException;
import elice.chargingstationbackend.charger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChargerService {
    private final ChargerRepository chargerRepository;
    private final BusinessOwnerRepository businessOwnerRepository;

    // 주변 충전소 리스트 자동 조회(+필터)
    public List<ChargerListResponseDTO> getNearbyChargerList(LocationDTO location, ChargerFilterDTO filter) {
        // 필터 값이 제공되지 않은 경우 빈 리스트를 null로 변환
        List<String> chgerType = filter.getChgerType().isEmpty() ? null : filter.getChgerType();
        List<String> output = filter.getOutput().isEmpty() ? null : filter.getOutput();
        Double chargingFee = filter.getChargingFee() == Double.MAX_VALUE ? null : filter.getChargingFee();
        String parkingFree = filter.getParkingFree().isEmpty() ? null : filter.getParkingFree();
        List<String> kind = filter.getKind().isEmpty() ? null : filter.getKind();
        List<Long> ownerIds = filter.getOwnerIds().isEmpty() ? null : filter.getOwnerIds();

        // Repository 호출
        List<Charger> nearByChargerList = chargerRepository.findChargerWithFilters(
                location.getUserLatitude(),
                location.getUserLongitude(),
                chgerType,
                output,
                chargingFee,
                parkingFree,
                kind,
                ownerIds
        );

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
    @Transactional
    public String addCharger(ChargerRequestDTO chargerRequestDTO) {
//         BusinessOwner accessOwnerId = businessOwnerRepository.findById(ownerId)
//                                 .orElseThrow(() -> new BusinessOwnerNotFoundException(ownerId));

        if (chargerRequestDTO.getStatId() == null || chargerRequestDTO.getStatId().isEmpty()) {
            chargerRequestDTO.setStatId(UUID.randomUUID().toString());
        }

        Charger newCharger = chargerRequestDTO.toEntity();
        chargerRepository.save(newCharger);

        return newCharger.getStatId();
    }

    // 충전소 수정
    @Transactional
    public void updateCharger(String statId, ChargerRequestDTO chargerRequestDTO) {
        Charger chargerToUpdate = chargerRepository.findById(statId)
                                .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + statId));
        
        chargerToUpdate.setStatNm(chargerRequestDTO.getStatNm());
        chargerToUpdate.setAddr(chargerRequestDTO.getAddr());
        chargerToUpdate.setChargingFee(chargerRequestDTO.getChargingFee());
        chargerToUpdate.setParkingFree(chargerRequestDTO.getOutput());
        chargerToUpdate.setChgerType(chargerRequestDTO.getChgerType());
        chargerToUpdate.setLat(chargerRequestDTO.getLat());
        chargerToUpdate.setLng(chargerRequestDTO.getLng());

        chargerRepository.save(chargerToUpdate);
    }

    // 충전소 삭제
    @Transactional
    public void deleteCharger(String statId) {
        Charger chargerToDelete = chargerRepository.findById(statId)
                                .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + statId));
        
        chargerRepository.delete(chargerToDelete);
    }
}
