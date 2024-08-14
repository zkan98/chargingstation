package elice.chargingstationbackend.charger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import elice.chargingstationbackend.charger.dto.ChargerApiResponseDTO;
import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.charger.repository.ChargerRepository;
import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.repository.BusinessOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final ChargerRepository chargerRepository;
    private final BusinessOwnerRepository businessOwnerRepository;

    private final String serviceKey = "4TbGmO/+UtIIPXlcDHVVZebt8muT2hdH8BixzgNuBePTYXaH3vbpY1PXhL7rZ1n7VrIR44UCyHU9DSMZLmTcAQ==";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JdbcTemplate jdbcTemplate;

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

        System.out.println("API 응답: " + response);

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
                // 변환 실패 시 원본 XML 반환
                return response;
            }
        }
        return response;
    }

    public void processAndSaveChargers() {
        int pageNo = 1;
        int numOfRows = 5000; // 페이지당 데이터 수
        int batchSize = 5000; // 배치 사이즈
        int maxPages = 500; // 최대 페이지 수 (무한 루프 방지)

        while (pageNo <= maxPages) {
            try {
                String jsonResponse = fetchChargerData(pageNo, numOfRows);
                ChargerApiResponseDTO dto = objectMapper.readValue(jsonResponse, ChargerApiResponseDTO.class);
                ChargerApiResponseDTO.Items items = dto.getItems();

                // 데이터가 비어있으면 종료
                if (items == null || items.getItemList() == null || items.getItemList().isEmpty()) {
                    System.out.println("불러올 데이터가 더 이상 없습니다.");
                    break;
                }

                List<ChargerApiResponseDTO.Item> itemList = items.getItemList();
                List<Charger> chargers = itemList.stream()
                    .map(this::createOrUpdateCharger)
                    .collect(Collectors.toList());

                for (int i = 0; i < chargers.size(); i += batchSize) {
                    int end = Math.min(i + batchSize, chargers.size());
                    List<Charger> batch = chargers.subList(i, end);
                    chargerRepository.saveAll(batch);
                    chargerRepository.flush();
                }

                pageNo++;

            } catch (IOException e) {
                // 실패 시 로깅 또는 알림 처리
                System.err.println("데이터를 불러오는 도중 에러발생: " + e.getMessage());
                e.printStackTrace();
                // 실패할 경우에는 계속 진행하거나 종료 결정
                break;
            }
        }
    }

    private Charger createOrUpdateCharger(ChargerApiResponseDTO.Item item) {
        // BusinessOwner를 먼저 조회하거나 없으면 새로 생성
        BusinessOwner businessOwner = businessOwnerRepository.findByBusinessId(item.getBusinessId())
            .orElseGet(() -> {
                BusinessOwner newOwner = new BusinessOwner();
                newOwner.setBusinessId(item.getBusinessId());
                newOwner.setBusinessName(item.getBusinessName());
                newOwner.setBusinessCall(item.getBusinessCall());
                newOwner.setBusinessCorporateName(item.getBusinessNameAlias());

                // User 엔티티에서 상속된 필드에 대한 초기화
                newOwner.setEmail(item.getBusinessId() + "@example.com"); // 사업자ID를 이메일에 사용 (실제 환경에 맞게 수정 필요)
                newOwner.setPassword("defaultPassword"); // 필요에 따라 수정 (패스워드 암호화 필요)
                newOwner.setUsername(item.getBusinessName()); // 사업자 이름을 username으로 사용
                newOwner.setAdmin(false); // 기본적으로 관리자 아님

                return businessOwnerRepository.save(newOwner);
            });

        // Charger 엔티티 생성
        Charger charger = item.toEntity();
        charger.setBusinessOwner(businessOwner);
        return charger;
    }

    // @Scheduled(fixedRate = 600000) // 10분마다 API 호출
//    public void scheduledFetchAndSaveChargers() {
//        processAndSaveChargers();
//    }
}
