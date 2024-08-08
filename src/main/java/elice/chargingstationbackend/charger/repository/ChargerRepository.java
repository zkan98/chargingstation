package elice.chargingstationbackend.charger.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elice.chargingstationbackend.charger.entity.Charger;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, String> {

    // 충전소 세부 조회
    Optional<Charger> findById(String statId);

    // 충전소 검색(충전소 이름, 장소)
    @Query("SELECT c FROM Charger c WHERE c.statNm LIKE %:searchTerm% OR c.addr LIKE %:searchTerm%")
    List<Charger> searchByChargerNameOrAddress(@Param("searchTerm") String searchTerm);

    // 주변 충전소 조회
    @Query(value = "SELECT * FROM charger c " +
            "WHERE ST_Distance_Sphere(POINT(c.lng, c.lat), POINT(:lng, :lat)) <= 5000 " +
            "ORDER BY ST_Distance_Sphere(POINT(c.lng, c.lat), POINT(:lng, :lat)) ASC " +
            "LIMIT 150",
            nativeQuery = true)
    List<Charger> findCharger(
            @Param("lat") Double userLatitude,
            @Param("lng") Double userLongitude);
}
