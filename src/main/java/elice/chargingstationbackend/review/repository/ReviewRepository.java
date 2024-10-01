package elice.chargingstationbackend.review.repository;

import elice.chargingstationbackend.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<Review> findByCharger_StatId(String statId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.charger.statId = :statId")
    Double getAverageRatingByStatId(String statId);
}
