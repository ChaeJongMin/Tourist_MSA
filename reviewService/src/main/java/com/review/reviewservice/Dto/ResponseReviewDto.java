package com.review.reviewservice.Dto;

import com.review.reviewservice.Domain.ReviewEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseReviewDto {
    private double score;
    private String reviewText;
    private boolean isUpdate;
    private String dateFormat;

    private String writer;

    public ResponseReviewDto(ReviewEntity review,String dateFormat, String writer){
        this.score=review.getScore();
        this.reviewText=review.getReviewText();
        this.dateFormat=dateFormat;
        this.isUpdate=review.isUpdate();
        this.writer=writer;
    }
}
