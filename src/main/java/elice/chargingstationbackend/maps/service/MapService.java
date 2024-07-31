package elice.chargingstationbackend.maps.service;

import elice.chargingstationbackend.maps.Entity.ChargeStationAPI;
import elice.chargingstationbackend.maps.mapsDto.ChargeStationDTO;
import elice.chargingstationbackend.maps.repository.ChargeStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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

    public List<ChargeStationDTO> getChargeStationsWithinDistance(Double lat, Double lng, double distance){

        double latRange = distance / 111.0;
        double lngRange = distance / (111.0 * Math.cos(Math.toRadians(lat)));

        double minLat = lat - latRange;
        double maxLat = lat + latRange;
        double minLng = lng - lngRange;
        double maxLng = lng + lngRange;

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
                            station.getPowerType(), station.getParkingFree(), station.getNote(), calculatedDistance);
                })
                .filter(dto -> dto.getDistance() < distance)
                .collect(Collectors.toList());
    }

    public String  getChargestationApi(int pageNo, int numOfRows, String zcode) throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=서비스키"); /*Service Key*/
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("zcode", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(zcode, StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        return sb.toString();
    }
}