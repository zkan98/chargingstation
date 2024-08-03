package elice.chargingstationbackend.maps.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import elice.chargingstationbackend.maps.Entity.ChargeStationAPI;
import elice.chargingstationbackend.maps.DTO.ChargeStationDTO;
import elice.chargingstationbackend.maps.repository.ChargeStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

@Service
public class MapService {

    private final ChargeStationRepository chargeStationRepository;

    @Autowired
    public MapService(ChargeStationRepository chargeStationRepository) {
        this.chargeStationRepository = chargeStationRepository;
    }

//    public String getChargestationApi(int pageNo, int numOfRows) throws IOException {
//
//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +
//                "=PRwlo8kAO7ZbkXrfg8pQL5YE346RF557TYQe%2F%2FW%2Bu3voecdcTdhYtC5SRD%2BzeTlRrccS%2BMDzqE%2BjwwBsJqmyWw%3D%3D"); ; /*Service Key*/
//        urlBuilder.append("&").append(URLEncoder.encode("pageNo",
//                StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
//        urlBuilder.append("&").append(URLEncoder.encode("numOfRows",
//                StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
//
//
//        URL url = new URL(urlBuilder.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        conn.setRequestProperty("Accept", "application/json");
//
//        BufferedReader rd;
//        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//
//        rd.close();
//        conn.disconnect();
//        return sb.toString();
//    }

    public List<ChargeStationDTO> getChargeStationsWithinDistance(Double lat, Double lng, double distance) throws IOException {

        double latRange = distance / 111.0;
        double lngRange = distance / (111.0 * Math.cos(Math.toRadians(lat)));

        double minLat = lat - latRange;
        double maxLat = lat + latRange;
        double minLng = lng - lngRange;
        double maxLng = lng + lngRange;


//        String response = getChargestationApi(1,9999);
//
//        if (response != null) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(response);
//            JsonNode itemsArray = rootNode.path("items");
//
//            if (itemsArray.isArray() && !itemsArray.isEmpty()) {
//                JsonNode itemsNode = itemsArray.get(0).path("item");
//                List<ChargeStationDTO> chargeStationList = new ArrayList<>();
//
//                if (itemsNode.isArray()) {
//                    for (JsonNode itemNode : itemsNode) {
//                        ChargeStationDTO chargeStationDTO = ChargeStationDTO.builder()
//                                .statNm(itemNode.path("statNm").asText())
//                                .chgerType(itemNode.path("chgerType").asText())
//                                .addr(itemNode.path("addr").asText())
//                                .lat(itemNode.path("lat").asDouble())
//                                .lng(itemNode.path("lng").asDouble())
//                                .useTime(itemNode.path("useTime").asText())
//                                .busiCall(itemNode.path("busiCall").asText())
//                                .stat(itemNode.path("stat").asInt())
//                                .powerType(itemNode.path("powerType").asText())
//                                .parkingFree(itemNode.path("parkingFree").asText())
//                                .note(itemNode.path("note").asText())
//                                .distance(0)
//                                .limitYn(itemNode.path("limitYn").asText())
//                                .limitDetail(itemNode.path("limitDetail").asText())
//                                .build();
//
//                        chargeStationRepository.save(chargeStationDTO.DtoToEntity(chargeStationDTO));
//
//                        double calculatedDistance = 6371 * Math.acos(
//                                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(chargeStationDTO.getLat())) *
//                                        Math.cos(Math.toRadians(chargeStationDTO.getLng()) - Math.toRadians(lng)) +
//                                        Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(chargeStationDTO.getLat()))
//                        );
//                        chargeStationDTO.setDistance(calculatedDistance);
//
//                        if (calculatedDistance <= distance) {
//                            chargeStationList.add(chargeStationDTO);
//                        }
//                    }
//                }
//
//                System.out.println(chargeStationList);
//                return chargeStationList;
//            }
//        }

        // response가 null일 경우 DB에서 조회
        List<ChargeStationAPI> chargeStations = chargeStationRepository.findWithinApproximateRange(minLat, maxLat, minLng, maxLng);

        return chargeStations.stream()
                .map(station -> {
                    double calculatedDistance = 6371 * Math.acos(
                            Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(station.getLat())) *
                                    Math.cos(Math.toRadians(station.getLng()) - Math.toRadians(lng)) +
                                    Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(station.getLat()))
                    );
                    return new ChargeStationDTO(station.getStatNm(), station.getChgerType(), station.getAddr(),
                            station.getLat(), station.getLng(), station.getUseTime(), station.getBusiCall(), station.getStat(),
                            station.getParkingFree(), calculatedDistance, station.getLimitYn());
                })
                .filter(dto -> dto.getDistance() < distance)
                .collect(Collectors.toList());
    }
}