package com.review.reviewservice.Dto;

import com.review.reviewservice.Domain.ReviewEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseReviewToUserDto {
    private Long reviewId;
    private String tourDestNm;
    private double score;
    private String reviewText;
    private boolean isUpdate;
    private String dateFormat;

    public ResponseReviewToUserDto(ReviewEntity review, String dateFormat, String tourDestNm){
        this.reviewId=review.getId();
        this.score=review.getScore();
        this.reviewText=review.getReviewText();
        this.dateFormat=dateFormat;
        this.isUpdate=review.isUpdate();
        this.tourDestNm=tourDestNm;
    }
}
