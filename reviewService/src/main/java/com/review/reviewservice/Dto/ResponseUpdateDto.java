package com.review.reviewservice.Dto;

import com.review.reviewservice.Domain.ReviewEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//클라이언트 측에서 수정한 리뷰 정보를 서버에 전달하기 위한 DTO 클래스
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
