package elice.chargingstationbackend.review.service;

import elice.chargingstationbackend.review.dto.ReviewDto;
import elice.chargingstationbackend.review.dto.ReviewRequestDto;
import elice.chargingstationbackend.review.entity.Review;
import elice.chargingstationbackend.review.repository.ReviewRepository;
import elice.chargingstationbackend.user.User;
import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.charger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ChargerRepository chargerRepository;

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Page<ReviewDto> getReviewsByStatId(String statId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByCharger_StatId(statId, pageable);

        return reviews.map(review -> {
            ReviewDto dto = new ReviewDto();
            dto.setReviewId(review.getReviewId());
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());
            dto.setTimestamp(review.getTimestamp());
            dto.setUsername(review.getUser().getUsername());
            return dto;
        });
    }

    public Double getAverageRatingByStatId(String statId) {
        Double averageRating = reviewRepository.getAverageRatingByStatId(statId);
        return (averageRating != null) ? averageRating : 0.0;
    }

    public Review updateReview(Long reviewId, ReviewRequestDto reviewDto) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리뷰 ID: " + reviewId));
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setTimestamp(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리뷰 ID: " + reviewId));
        reviewRepository.delete(review);
    }
}
