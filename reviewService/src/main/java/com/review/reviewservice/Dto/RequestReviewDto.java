package com.review.reviewservice.Dto;

import com.review.reviewservice.Domain.ReviewEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//클라이언트측에서 작성한 리뷰를 서버에 전달하기 위한 DTO 클래스
public class RequestReviewDto {
    private String tourDestNm;
    private String userNm;
    private String reviewText;
    private double score;

    public ReviewEntity toReviewEntity(Long userId, Long tourId){
        return ReviewEntity.builder()
                .userId(userId)
                .touristId(tourId)
                .score(score)
                .reviewText(reviewText)
                .build();
    }

}
