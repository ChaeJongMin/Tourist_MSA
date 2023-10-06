package com.review.reviewservice.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestUpdateDto {
    private String reviewTexts;
    private double score;
}
