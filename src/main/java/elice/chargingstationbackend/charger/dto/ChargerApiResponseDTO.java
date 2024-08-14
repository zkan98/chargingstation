package elice.chargingstationbackend.charger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import elice.chargingstationbackend.charger.entity.Charger;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargerApiResponseDTO {

    @JsonProperty("resultMsg")
    private String resultMsg;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("items")
    private Items items;

    @JsonProperty("pageNo")
    private int pageNo;

    @JsonProperty("resultCode")
    private String resultCode;

    @JsonProperty("numOfRows")
    private int numOfRows;

    @Data
    public static class Items {
        @JsonProperty("item")
        private List<Item> itemList;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        @JsonProperty("statNm")
        private String name;          // 프론트엔드의 name에 해당

        @JsonProperty("statId")
        private String id;            // 프론트엔드의 id에 해당

        @JsonProperty("chgerId")
        private String connectorId;   // chgerId로 일치 (필요에 따라 이름 변경 가능)

        @JsonProperty("chgerType")
        private String connector;     // 프론트엔드의 connector에 해당

        @JsonProperty("addr")
        private String address;       // 프론트엔드의 address에 해당

        @JsonProperty("lat")
        private Double latitude;

        @JsonProperty("lng")
        private Double longitude;

        @JsonProperty("useTime")
        private String useTime;

        @JsonProperty("busiId")
        private String businessId;

        @JsonProperty("bnm")
        private String businessName;  // 프론트엔드의 businessName에 해당

        @JsonProperty("busiNm")
        private String businessNameAlias; // Alias for businessName if needed

        @JsonProperty("busiCall")
        private String businessCall;  // 프론트엔드의 businessCall에 해당

        @JsonProperty("stat")
        private String status;        // 프론트엔드의 status에 해당

        @JsonProperty("statUpdDt")
        private String statusUpdateDate;

        @JsonProperty("powerType")
        private String powerType;

        @JsonProperty("output")
        private String speed;         // 프론트엔드의 speed에 해당

        @JsonProperty("method")
        private String method;

        @JsonProperty("zcode")
        private String zcode;

        @JsonProperty("zscode")
        private String zscode;

        @JsonProperty("kind")
        private String kind;

        @JsonProperty("kindDetail")
        private String kindDetail;

        @JsonProperty("parkingFree")
        private String parkingFee;    // 프론트엔드의 parkingFee에 해당

        @JsonProperty("speed")
        private String speedAlias;    // Alias for speed if needed

        @JsonProperty("limitYn")
        private String limitYn;

        @JsonProperty("limitDetail")
        private String limitDetail;

        public Charger toEntity() {
            return Charger.builder()
                .statNm(name)
                .statId(id)
                .chgerId(connectorId)
                .chgerType(connector)
                .addr(address)
                .lat(latitude)
                .lng(longitude)
                .useTime(useTime)
                .stat(status)
                .statUpdDt(statusUpdateDate)
                .output(speed)
                .method(method)
                .zcode(zcode)
                .zscode(zscode)
                .kind(kind)
                .kindDetail(kindDetail)
                .parkingFree(parkingFee)
                .limitYn(limitYn)
                .limitDetail(limitDetail)
                .build();
        }
    }
}
