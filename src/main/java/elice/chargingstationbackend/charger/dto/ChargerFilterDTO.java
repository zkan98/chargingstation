package elice.chargingstationbackend.charger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerFilterDTO {
    private List<String> chgerType = Collections.emptyList(); // 빈 리스트 기본값
    private List<String> output = Collections.emptyList();    // 빈 리스트 기본값
    private Double chargingFee = Double.MAX_VALUE; // 최대값 기본값
    private String parkingFree = ""; // 빈 문자열 기본값
    private List<String> kind = Collections.emptyList();         // 빈 리스트 기본값
    private List<Long> ownerIds = Collections.emptyList();       // 빈 리스트 기본값
}
