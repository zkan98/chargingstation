package elice.chargingstationbackend.charger.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import elice.chargingstationbackend.charger.dto.ChargerDetailResponseDTO;
import elice.chargingstationbackend.charger.dto.ChargerFilterResponseDTO;
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
    public List<ChargerListResponseDTO> getNearbyChargerList(Double userLatitude, Double userLongitude) {
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
    public ChargerDetailResponseDTO getChagerDetail(Long chargerId) {
        Charger chargerDetail = chargerRepository.findById(chargerId)
                                        .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + chargerId));
        return new ChargerDetailResponseDTO(chargerDetail);
    }

    // 충전소 추가
    public Long addCharger(ChargerRequestDTO chargerRequestDTO) {
        // BusinessOwner accessOwnerId = businessOwnerRepository.findById(ownerId)
        //                         .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        Charger newCharger = chargerRequestDTO.toEntity();
        chargerRepository.save(newCharger);

        return newCharger.getId();
    }

    // 충전소 수정
    public void updateCharger(Long chargerId, ChargerRequestDTO chargerRequestDTO) {
        Charger chargerToUpdate = chargerRepository.findById(chargerId)
                                .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + chargerId));
        
        chargerToUpdate.setChargerName(chargerRequestDTO.getChargerName());
        chargerToUpdate.setAddress(chargerRequestDTO.getAddress());
        chargerToUpdate.setSlots(chargerRequestDTO.getSlots());
        chargerToUpdate.setChargingSpeed(chargerRequestDTO.getChargingSpeed());
        chargerToUpdate.setChargingFee(chargerRequestDTO.getChargingFee());
        chargerToUpdate.setConnectorType(chargerRequestDTO.getConnectorType());
        chargerToUpdate.setParkingFee(chargerRequestDTO.getParkingFee());

        chargerRepository.save(chargerToUpdate);
    }

    // 충전소 삭제
    public void deleteCharger(Long chargerId) {
        Charger chargerToDelete = chargerRepository.findById(chargerId)
                                .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. 충전소 식별번호 : " + chargerId));
        
        chargerRepository.delete(chargerToDelete);
    }
}
