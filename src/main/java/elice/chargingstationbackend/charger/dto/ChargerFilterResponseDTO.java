package elice.chargingstationbackend.charger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerFilterResponseDTO {
    private Long chargerId;
    private String businessName;
    private String connectorType;
    private String chargerSpeed;
    private String chargerFee;
}
