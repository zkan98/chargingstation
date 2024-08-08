package elice.chargingstationbackend.review.controller;

import elice.chargingstationbackend.review.entity.Review;
import elice.chargingstationbackend.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.saveReview(review));
    }

    @GetMapping("/charger/{chgerId}")
    public ResponseEntity<List<Review>> getReviewsByChargerId(@PathVariable String chgerId) {
        return ResponseEntity.ok(reviewService.getReviewsByChargerId(chgerId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review reviewDetails) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, reviewDetails));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
