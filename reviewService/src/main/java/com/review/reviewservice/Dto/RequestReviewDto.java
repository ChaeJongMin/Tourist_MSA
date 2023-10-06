package com.review.reviewservice.Dto;

import com.review.reviewservice.Domain.ReviewEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
