package com.userinfo.userservice.Dto.Response;

import lombok.Getter;

@Getter
public class ResponseReviewToUserDto {
    private Long reviewId;
    private String tourDestNm;
    private double score;
    private String reviewText;
    private boolean isUpdate;
    private String dateFormat;

}
