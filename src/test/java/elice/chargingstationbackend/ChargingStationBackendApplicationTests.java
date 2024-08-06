package elice.chargingstationbackend;

import elice.chargingstationbackend.charger.util.LocationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ChargingStationBackendApplicationTests {

    @Test
    void contextLoads() {
        // 중심 경도
        double centerLat = 37.5665; // 예시: 서울 위도
        double centerLon = 126.9780; // 예시: 서울 경도

        // 데이터 리스트
        List<Map<String, Object>> dataList = new ArrayList<>();
        // 예시 데이터 추가
        Map<String, Object> data1 = new HashMap<>();
        data1.put("latitude", 37.5651);
        data1.put("longitude", 126.9895);
        data1.put("name", "Location 1");

        Map<String, Object> data2 = new HashMap<>();
        data2.put("latitude", 37.5700);
        data2.put("longitude", 126.9920);
        data2.put("name", "Location 2");

        dataList.add(data1);
        dataList.add(data2);

        // 필터링된 데이터 리스트
        List<Map<String, Object>> filteredList = LocationFilter.filterLocations(centerLat, centerLon, dataList);

        // 결과 출력
        for (Map<String, Object> data : filteredList) {
            System.out.println(data.get("name"));
        }
    }
}


