package elice.chargingstationbackend.maps.repository;

import elice.chargingstationbackend.maps.Entity.ChargeStationAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChargeStationRepository extends JpaRepository<ChargeStationAPI, Long> {
    @Query("SELECT ch FROM ChargeStationAPI ch WHERE ch.lat BETWEEN :minLat AND :maxLat AND ch.lng BETWEEN :minLng AND :maxLng")
    List<ChargeStationAPI> findWithinApproximateRange(@Param("minLat") Double minLat, @Param("maxLat") Double maxLat, @Param("minLng") Double minLng, @Param("maxLng") Double maxLng);


}
