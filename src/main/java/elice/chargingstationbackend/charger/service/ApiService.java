package elice.chargingstationbackend.charger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import elice.chargingstationbackend.charger.dto.ChargerApiResponseDTO;
import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.charger.repository.ChargerRepository;
import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.repository.BusinessOwnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final ChargerRepository chargerRepository;
    private final BusinessOwnerRepository businessOwnerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final String serviceKey = "4TbGmO/+UtIIPXlcDHVVZebt8muT2hdH8BixzgNuBePTYXaH3vbpY1PXhL7rZ1n7VrIR44UCyHU9DSMZLmTcAQ==";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String fetchChargerData(int pageNo, int numOfRows) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(numOfRows), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        int responseCode = conn.getResponseCode();
        if (responseCode >= 200 && responseCode <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String response = sb.toString();

        // XML을 JSON으로 변환
        response = convertXmlToJsonIfNecessary(response);

        return response;
    }

    private String convertXmlToJsonIfNecessary(String response) {
        if (response.startsWith("<")) {
            try {
                JSONObject jsonObject = XML.toJSONObject(response);
                return jsonObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return response;
            }
        }
        return response;
    }

    public void processAndSaveChargers() {
        int pageNo = 1;
        int numOfRows = 5000;
        int batchSize = 5000;
        int maxPages = 500;

        Map<String, BusinessOwner> ownerCache = new HashMap<>();

        while (pageNo <= maxPages) {
            try {
                String jsonResponse = fetchChargerData(pageNo, numOfRows);
                ChargerApiResponseDTO dto = objectMapper.readValue(jsonResponse, ChargerApiResponseDTO.class);
                ChargerApiResponseDTO.Items items = dto.getItems();

                if (items == null || items.getItemList() == null || items.getItemList().isEmpty()) {
                    System.out.println("불러올 데이터가 더 이상 없습니다.");
                    break;
                }

                List<ChargerApiResponseDTO.Item> itemList = items.getItemList();
                List<Charger> chargers = itemList.stream()
                    .map(item -> createOrUpdateCharger(item, ownerCache))
                    .collect(Collectors.toList());

                // 배치로 저장
                for (int i = 0; i < chargers.size(); i += batchSize) {
                    int end = Math.min(i + batchSize, chargers.size());
                    List<Charger> batch = chargers.subList(i, end);
                    chargerRepository.saveAll(batch);
                    chargerRepository.flush();
                    entityManager.clear();
                }

                pageNo++;

            } catch (IOException e) {
                System.err.println("데이터를 불러오는 도중 에러발생: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    private Charger createOrUpdateCharger(ChargerApiResponseDTO.Item item, Map<String, BusinessOwner> ownerCache) {
        // BusinessOwner를 먼저 조회하거나 없으면 새로 생성
        BusinessOwner businessOwner = ownerCache.computeIfAbsent(item.getBusiId(), busiId -> {
            return businessOwnerRepository.findByBusinessId(busiId)
                .orElseGet(() -> {
                    BusinessOwner newOwner = new BusinessOwner();
                    newOwner.setBusinessId(item.getBusiId());
                    newOwner.setBusinessName(item.getBusiNm());
                    newOwner.setBusinessCall(item.getBusiCall());
                    newOwner.setBusinessCorporateName(item.getBnm());

                    // User 엔티티에서 상속된 필드에 대한 초기화
                    newOwner.setEmail(item.getBusiId() + "@example.com");
                    newOwner.setPassword("defaultPassword");
                    newOwner.setUsername(item.getBusiNm());
                    newOwner.setAdmin(false);

                    return businessOwnerRepository.save(newOwner);
                });
        });

        Charger charger = item.toEntity(); // item 객체를 Charger 엔티티로 변환
        charger.setBusinessOwner(businessOwner); // BusinessOwner 매핑

        return chargerRepository.save(charger); // Charger 엔티티 저장
    }

}
