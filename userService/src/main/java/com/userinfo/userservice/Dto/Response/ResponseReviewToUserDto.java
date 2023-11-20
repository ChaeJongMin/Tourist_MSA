package com.userinfo.userservice.Dto.Response;

import lombok.Getter;

//자신의 리뷰 정보를 클라이언트측으로 보내기 위한 DTO 클래스
@Getter
public class ResponseReviewToUserDto {
    private Long reviewId;
    private String tourDestNm;
    private double score;
    private String reviewText;
    private boolean isUpdate;
    private String dateFormat;

}
