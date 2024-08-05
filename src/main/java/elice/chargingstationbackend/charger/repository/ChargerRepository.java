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
    
    // @Query("SELECT id, name, ST_AsText(location) AS location" +
    // "FROM locations" + 
    // "WHERE ST_Distance_Sphere(location, ST_GeomFromText('POINT(? - ?)')) < 5000;")
    // Page<Charger> findChargerByLocation(Pageable pageable);

    // 충전소 세부 조회
    Optional<Charger> findById(Long chargerId);

    // 충전소 검색(충전소 이름, 장소)
    @Query("SELECT c FROM Charger c WHERE c.chargerName LIKE %:searchTerm% OR c.address LIKE %:searchTerm%")
    List<Charger> searchByChargerNameOrAddress(@Param("searchTerm") String searchTerm);

    // 주변 충전소 조회( +필터)
    @Query("SELECT c FROM Charger c WHERE " +
       "(:connectorOption IS NULL OR (c.connectorType = :connectorOption AND :connectorOption IS NOT NULL)) AND " +
       "(:speedOption IS NULL OR (c.speed = :speedOption AND :speedOption IS NOT NULL)) AND " +
       "(:feeOption IS NULL OR (c.fee <= :feeOption AND :feeOption IS NOT NULL)) AND " +
       "(:bnameOption IS NULL OR (c.brandName = :bnameOption AND :chargable IS NOT NULL)) AND " +
       "(:chargable IS NULL OR (c.availableSlots != 0 AND :chargable IS NOT NULL)) AND " +
       "((6371 * acos( " +
       "cos(radians(:latitude)) * cos(radians(c.latitude)) * " +
       "cos(radians(c.longitude) - radians(:longitude)) + " +
       "sin(radians(:latitude)) * sin(radians(c.latitude)) " +
       ")) <= 5)")
Page<Charger> findChargers(
        @Param("latitude") Double userLatitude,
        @Param("longitude") Double userLongitude,
        @Param("connectorOption") String connectorOption,
        @Param("speedOption") String speedOption,
        @Param("feeOption") String feeOption,
        @Param("bnameOption") String bnameOption,
        @Param("chargable") String chargable,
        Pageable pageable);
}
