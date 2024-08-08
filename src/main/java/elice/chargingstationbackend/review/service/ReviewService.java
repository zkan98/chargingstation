package elice.chargingstationbackend.review.service;

import elice.chargingstationbackend.review.entity.Review;
import elice.chargingstationbackend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByChargerId(String chgerId) {
        return reviewRepository.findByCharger_ChgerId(chgerId);
    }

    public Review updateReview(Long reviewId, Review reviewDetails) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setTimestamp(reviewDetails.getTimestamp());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));
        reviewRepository.delete(review);
    }
}
