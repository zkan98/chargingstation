package elice.chargingstationbackend.charger.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Double[] getCoordinates(String address) {
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("address", address)
                .queryParam("key", apiKey)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        System.out.println("API Response: " + response);

        JsonNode jsonNode = parseJson(response);

        if (jsonNode != null && jsonNode.path("status").asText().equals("OK")) {
            JsonNode location = jsonNode.path("results").get(0).path("geometry").path("location");
            double lat = location.path("lat").asDouble();
            double lng = location.path("lng").asDouble();
            return new Double[]{lat, lng};
        } else {
            throw new RuntimeException("Geocoding API 요청 실패");
        }
    }

    private JsonNode parseJson(String response) {
        try {
            return new ObjectMapper().readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("JSON 변환실패", e);
        }
    }
}