package elice.chargingstationbackend.charger.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elice.chargingstationbackend.charger.entity.Charger;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, String>, JpaSpecificationExecutor<Charger> {

    // 충전소 세부 조회
    Optional<Charger> findById(String statId);

    // 충전소 검색(충전소 이름, 장소)
    @Query(value = "SELECT * FROM charger c WHERE c.stat_nm LIKE %:searchTerm% OR c.addr LIKE %:searchTerm% LIMIT 5", nativeQuery = true)
    List<Charger> searchByChargerNameOrAddress(@Param("searchTerm") String searchTerm);

    // 사업자별 충전소 조회
    @Query("SELECT c FROM Charger c WHERE c.businessOwner.id = :ownerId")
    List<Charger> findChargersByOwnerId(@Param("ownerId") Long ownerId);
}
