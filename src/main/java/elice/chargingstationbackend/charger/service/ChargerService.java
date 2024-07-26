package elice.chargingstationbackend.charger.service;

import java.util.Optional;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import elice.chargingstationbackend.charger.dto.ChargerDetailResponseDTO;
import elice.chargingstationbackend.charger.dto.ChargerListResponseDTO;
import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.charger.exception.ChargerNotFoundException;
import elice.chargingstationbackend.charger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargerService {
    private final ChargerRepository chargerRepository;

    // 주변 충전소 리스트 자동 조회
    // public Page<ChargerListResponseDTO> getNearbyChargerList(Double latitute, Double longitute) {
    //     chargerRepository.findAll();

    // }

    // 충전소 세부 조회
    public ChargerDetailResponseDTO getChagerDetail(Long chargerId) {
        Charger chargerDetail = chargerRepository.findById(chargerId)
                                        .orElseThrow(() -> new ChargerNotFoundException("해당 충전소를 찾을 수 없습니다. ID: " + chargerId));
        return new ChargerDetailResponseDTO(chargerDetail);
    }
}
