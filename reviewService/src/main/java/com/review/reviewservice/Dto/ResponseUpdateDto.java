package com.review.reviewservice.Dto;

import com.review.reviewservice.Domain.ReviewEntity;

public class ResponseUpdateDto {

    private double score;
    private String reviewText;
    private boolean isUpdate;
    private String dateFormat;

    public ResponseUpdateDto(ReviewEntity reviewEntity, String dateFormat){
        this.score = reviewEntity.getScore();
        this.reviewText = reviewEntity.getReviewText();
        this.isUpdate = reviewEntity.isUpdate();
        this.dateFormat = dateFormat;
    }
}
