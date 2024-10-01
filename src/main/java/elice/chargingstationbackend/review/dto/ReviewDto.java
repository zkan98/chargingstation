package elice.chargingstationbackend.review.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {
    private Long reviewId;
    private double rating;
    private String comment;
    private String username; // 사용자 이름 추가
    private LocalDateTime timestamp;
}
