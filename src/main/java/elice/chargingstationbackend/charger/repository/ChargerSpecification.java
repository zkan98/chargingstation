package elice.chargingstationbackend.charger.repository;

import elice.chargingstationbackend.charger.entity.Charger;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
public class ChargerSpecification implements Specification<Charger> {

    private final Double userLatitude;
    private final Double userLongitude;
    private final List<String> chgerType;
    private final List<String> output;
    private final Double chargingFee;
    private final String parkingFree;
    private final List<String> kind;
    private final List<Long> ownerIds;

    @Override
    public Predicate toPredicate(Root<Charger> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // 거리 계산을 위한 좌표 생성
        Expression<Double> userLat = criteriaBuilder.literal(userLatitude);
        Expression<Double> userLng = criteriaBuilder.literal(userLongitude);
        Expression<Double> chargerLat = root.get("lat");
        Expression<Double> chargerLng = root.get("lng");

        // 거리 조건
        Expression<Double> distance = criteriaBuilder.function("ST_Distance_Sphere", Double.class,
                criteriaBuilder.function("POINT", Double.class, chargerLng, chargerLat),
                criteriaBuilder.function("POINT", Double.class, userLng, userLat));
        Predicate distancePredicate = criteriaBuilder.lessThanOrEqualTo(distance, 5000.0);

        // 조건 생성
        Predicate finalPredicate = criteriaBuilder.conjunction();

        // 필터 조건 추가
        if (chgerType != null && !chgerType.isEmpty()) {
            finalPredicate = criteriaBuilder.and(finalPredicate, root.get("chgerType").in(chgerType));
        }

        if (output != null && !output.isEmpty()) {
            finalPredicate = criteriaBuilder.and(finalPredicate, root.get("output").in(output));
        }

        if (chargingFee != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.lessThanOrEqualTo(root.get("chargingFee"), chargingFee));
        }

        if (parkingFree != null && !parkingFree.isEmpty()) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("parkingFree"), parkingFree));
        }

        if (kind != null && !kind.isEmpty()) {
            finalPredicate = criteriaBuilder.and(finalPredicate, root.get("kind").in(kind));
        }

        if (ownerIds != null && !ownerIds.isEmpty()) {
            finalPredicate = criteriaBuilder.and(finalPredicate, root.get("businessOwner").get("id").in(ownerIds));
        }

        // 모든 조건을 결합
        query.where(criteriaBuilder.and(distancePredicate, finalPredicate));

        // 거리 기준으로 정렬
        query.orderBy(criteriaBuilder.asc(distance));

        return query.getRestriction();
    }
}