package elice.chargingstationbackend.charger.service;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.exception.BusinessOwnerNotFoundException;
import elice.chargingstationbackend.business.repository.BusinessOwnerRepository;
import elice.chargingstationbackend.charger.dto.*;
import elice.chargingstationbackend.charger.repository.ChargerSpecification;
import org.springframework.data.jpa.domain.Specification;
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
    private final GeocodingService geocodingService;

    // 주변 충전소 리스트 자동 조회(+필터)
    public List<ChargerListResponseDTO> getNearbyChargerList(LocationDTO location, List<String> chgerType,
        List<String> output, Double chargingFee,
        String parkingFree, List<String> kind, List<Long> ownerIds) {

        Specification<Charger> spec = new ChargerSpecification(
            location.getUserLatitude(),
            location.getUserLongitude(),
            chgerType,
            output,
            chargingFee,
            parkingFree,
            kind,
            ownerIds
        );

        List<Charger> nearByChargerList = chargerRepository.findAll(spec);
        return nearByChargerList.stream()
            .limit(50)
            .map(ChargerListResponseDTO::new)
            .collect(Collectors.toList());
    }

    // 충전소 검색(충전소 이름, 장소)
    public List<ChargerListResponseDTO> searchCharger(String searchTerm) {
        List<Charger> resultsList = chargerRepository.searchByChargerNameOrAddress(searchTerm);
        return resultsList.stream()
            .map(ChargerListResponseDTO::new)
            .collect(Collectors.toList());
    }

    // 충전소 세부 조회
    public ChargerDetailResponseDTO getChagerDetail(String statId) {
        Charger chargerDetail = chargerRepository.findById(statId)
            .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + statId));
        return new ChargerDetailResponseDTO(chargerDetail);
    }

    // 사업자별 충전소 조회
    public List<ChargerListResponseDTO> getOwnerChargerList(Long ownerId) {
        List<Charger> ownerChargerPage = chargerRepository.findChargersByOwnerId(ownerId);

        // 충전소가 없을 때 빈 리스트를 반환
        if (ownerChargerPage.isEmpty()) {
            return Collections.emptyList();  // 빈 리스트 반환
        }

        return ownerChargerPage.stream()
            .map(ChargerListResponseDTO::new)
            .collect(Collectors.toList());
    }


    // 충전소 추가
    @Transactional
    public String addCharger(ChargerRequestDTO chargerRequestDTO, BusinessOwner businessOwner) {
        Double[] coordinates = geocodingService.getCoordinates(chargerRequestDTO.getAddr());
        chargerRequestDTO.setLat(coordinates[0]);
        chargerRequestDTO.setLng(coordinates[1]);

        if (chargerRequestDTO.getStatId() == null || chargerRequestDTO.getStatId().isEmpty()) {
            chargerRequestDTO.setStatId(UUID.randomUUID().toString());
        }

        Charger newCharger = chargerRequestDTO.toEntity(businessOwner);
        chargerRepository.save(newCharger);

        return newCharger.getStatId();
    }

    // 충전소 수정
    @Transactional
    public void updateCharger(String statId, ChargerRequestDTO chargerRequestDTO) {
        Charger chargerToUpdate = chargerRepository.findById(statId)
            .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + statId));

        if (!chargerToUpdate.getAddr().equals(chargerRequestDTO.getAddr())) {
            Double[] coordinates = geocodingService.getCoordinates(chargerRequestDTO.getAddr());
            chargerToUpdate.setLat(coordinates[0]);
            chargerToUpdate.setLng(coordinates[1]);
        }

        chargerToUpdate.setStatNm(chargerRequestDTO.getStatNm());
        chargerToUpdate.setAddr(chargerRequestDTO.getAddr());
        chargerToUpdate.setChargingFee(chargerRequestDTO.getChargingFee());
        chargerToUpdate.setParkingFree(chargerRequestDTO.getParkingFree());
        chargerToUpdate.setChgerType(chargerRequestDTO.getChgerType());

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
