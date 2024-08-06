package elice.chargingstationbackend.charger.util;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class LocationFilter {
    public static List<Map<String, Object>> filterLocations(double centerLat, double centerLon, List<Map<String, Object>> dataList) {
        List<Map<String, Object>> filteredList = new ArrayList<>();

        for (Map<String, Object> data : dataList) {
            double lat = (double) data.get("latitude");
            double lon = (double) data.get("longitude");

            double distance = DistanceCalculator.calculateDistance(centerLat, centerLon, lat, lon);

            if (distance <= 5.0) {
                filteredList.add(data);
            }
        }
        return filteredList;
    }
}