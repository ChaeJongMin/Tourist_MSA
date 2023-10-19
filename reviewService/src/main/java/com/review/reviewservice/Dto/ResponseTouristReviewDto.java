package com.review.reviewservice.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ResponseTouristReviewDto {
    Boolean isReviewed;
    List<ResponseReviewDto> reviewList;
    public ResponseTouristReviewDto(Boolean flag , List<ResponseReviewDto> reviewList){
        isReviewed = flag;
        this.reviewList = reviewList;
    }
}
