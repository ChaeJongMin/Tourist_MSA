package com.review.reviewservice.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
//자신이 쓴 리뷰인지 파악하기 위해 기존 ResponseReviewDto 클래스를 리스트형태로 가지고 있는 DTO 클래스
//실제로 관광지에 대한 리뷰를 보기 원할 시 전달
public class ResponseTouristReviewDto {
    Boolean isReviewed;
    List<ResponseReviewDto> reviewList;
    public ResponseTouristReviewDto(Boolean flag , List<ResponseReviewDto> reviewList){
        isReviewed = flag;
        this.reviewList = reviewList;
    }
}
