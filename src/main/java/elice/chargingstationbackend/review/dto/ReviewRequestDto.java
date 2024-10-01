// ReviewRequestDto.java (새로 생성)

package elice.chargingstationbackend.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private double rating;
    private String comment;
    private String statId; // Charger의 statId를 직접 받음
}
