package elice.chargingstationbackend.charger.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

@Service
public class GeocodingService {

    @Value("${kakao.maps.api.key}")
    private String apiKey; // 발급받은 API Key

    public Double[] getCoordinates(String address) {
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String response = responseEntity.getBody();

        if (response != null) {
            JsonNode jsonNode = parseJson(response);

            if (jsonNode != null && jsonNode.path("documents").size() > 0) {
                JsonNode addressNode = jsonNode.path("documents").get(0).path("address");
                double lng = addressNode.path("x").asDouble();
                double lat = addressNode.path("y").asDouble();
                return new Double[]{lat, lng};
            }
        }
        return null;
    }

    private JsonNode parseJson(String response) {
        try {
            return new ObjectMapper().readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }
}