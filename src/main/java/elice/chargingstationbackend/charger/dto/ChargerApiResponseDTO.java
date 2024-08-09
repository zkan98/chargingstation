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
        private String statNm;

        @JsonProperty("statId")
        private String statId;

        @JsonProperty("chgerId")
        private String chgerId;

        @JsonProperty("chgerType")
        private String chgerType;

        @JsonProperty("addr")
        private String addr;

        @JsonProperty("lat")
        private Double lat;

        @JsonProperty("lng")
        private Double lng;

        @JsonProperty("useTime")
        private String useTime;

        @JsonProperty("busiId")
        private String busiId;

        @JsonProperty("bnm")
        private String bnm;

        @JsonProperty("busiNm")
        private String busiNm;

        @JsonProperty("busiCall")
        private String busiCall;

        @JsonProperty("stat")
        private String stat;

        @JsonProperty("statUpdDt")
        private String statUpdDt;

        @JsonProperty("powerType")
        private String powerType;

        @JsonProperty("output")
        private String output;

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
        private String parkingFree;

        @JsonProperty("speed")
        private String speed;

        @JsonProperty("limitYn")
        private String limitYn;

        @JsonProperty("limitDetail")
        private String limitDetail;

        public Charger toEntity() {
            return Charger.builder()
                .statNm(statNm)
                .statId(statId)
                .chgerId(chgerId)
                .chgerType(chgerType)
                .addr(addr)
                .lat(lat)
                .lng(lng)
                .useTime(useTime)
                .stat(stat)
                .statUpdDt(statUpdDt)
                .output(output)
                .method(method)
                .zcode(zcode)
                .zscode(zscode)
                .kind(kind)
                .kindDetail(kindDetail)
                .parkingFree(parkingFree)
                .limitYn(limitYn)
                .limitDetail(limitDetail)
                .build();
        }
    }
}
