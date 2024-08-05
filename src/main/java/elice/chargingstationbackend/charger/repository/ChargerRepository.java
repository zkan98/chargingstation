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
public interface ChargerRepository extends JpaRepository<Charger, Long> {

    // 충전소 세부 조회
    Optional<Charger> findById(Long chargerId);

    // 충전소 검색(충전소 이름, 장소)
    @Query("SELECT c FROM Charger c WHERE c.chargerName LIKE %:searchTerm% OR c.address LIKE %:searchTerm%")
    List<Charger> searchByChargerNameOrAddress(@Param("searchTerm") String searchTerm);

    // 주변 충전소 조회
    @Query("SELECT c FROM Charger c WHERE " +
            "(6371 * acos( " +
            "cos(radians(:latitude)) * cos(radians(c.latitude)) * " +
            "cos(radians(c.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(c.latitude)) " +
            ")) <= 10")  // 10km 이내
    List<Charger> findCharger(
            @Param("latitude") Double userLatitude,
            @Param("longitude") Double userLongitude);
}
