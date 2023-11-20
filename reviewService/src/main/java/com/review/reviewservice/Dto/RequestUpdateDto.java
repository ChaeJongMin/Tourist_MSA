package com.review.reviewservice.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//클라이언트측에서 수정한 리뷰를 서버에 보내기 위한 DTO 클래스
public class RequestUpdateDto {
    private String reviewTexts;
    private double score;
}
