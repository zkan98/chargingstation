package elice.chargingstationbackend.review.controller;

import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.charger.repository.ChargerRepository;
import elice.chargingstationbackend.review.dto.ReviewDto;
import elice.chargingstationbackend.review.dto.ReviewRequestDto;
import elice.chargingstationbackend.review.entity.Review;
import elice.chargingstationbackend.review.service.ReviewService;
import elice.chargingstationbackend.user.User;
import elice.chargingstationbackend.user.UserRepository;
import elice.chargingstationbackend.user.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;

    // 리뷰 생성 엔드포인트
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReview(@RequestHeader("Authorization") String token, @RequestBody ReviewRequestDto reviewDto) {
        // 토큰에서 Bearer 접두사 제거
        String jwtToken = token.replace("Bearer ", "");

        // 토큰에서 사용자 정보 추출 및 검증
        Claims claims;
        try {
            claims = jwtUtil.extractToken(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        String email = claims.getSubject();  // 토큰의 subject에서 이메일 가져오기

        // 사용자 정보 조회
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        User user = userOptional.get();

        // 요청에서 statId 가져오기
        String statId = reviewDto.getStatId();

        // Charger 엔티티 조회
        Optional<Charger> chargerOptional = chargerRepository.findById(statId);
        if (chargerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("충전소를 찾을 수 없습니다.");
        }
        Charger charger = chargerOptional.get();

        // Review 엔티티 생성 및 설정
        Review review = new Review();
        review.setUser(user);
        review.setCharger(charger);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setTimestamp(LocalDateTime.now());

        // 리뷰 저장
        Review savedReview = reviewService.saveReview(review);

        // ReviewDto로 변환하여 반환
        ReviewDto responseDto = new ReviewDto();
        responseDto.setReviewId(savedReview.getReviewId());
        responseDto.setRating(savedReview.getRating());
        responseDto.setComment(savedReview.getComment());
        responseDto.setTimestamp(savedReview.getTimestamp());
        responseDto.setUsername(user.getUsername()); // 사용자 이름 설정

        return ResponseEntity.ok(responseDto);
    }

    // 특정 충전소의 리뷰 목록 조회
    @GetMapping("/charger/{statId}")
    public ResponseEntity<Page<ReviewDto>> getReviewsByStatId(
        @PathVariable String statId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewDto> reviews = reviewService.getReviewsByStatId(statId, pageable);
        return ResponseEntity.ok(reviews);
    }

    // 특정 충전소의 평균 평점 조회
    @GetMapping("/charger/{statId}/average-rating")
    public ResponseEntity<Double> getAverageRatingByStatId(@PathVariable String statId) {
        Double averageRating = reviewService.getAverageRatingByStatId(statId);
        return ResponseEntity.ok(averageRating);
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewDto) {
        Review updatedReview = reviewService.updateReview(reviewId, reviewDto);

        // ReviewDto로 변환하여 반환
        ReviewDto dto = new ReviewDto();
        dto.setReviewId(updatedReview.getReviewId());
        dto.setRating(updatedReview.getRating());
        dto.setComment(updatedReview.getComment());
        dto.setTimestamp(updatedReview.getTimestamp());
        dto.setUsername(updatedReview.getUser().getUsername());

        return ResponseEntity.ok(dto);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
