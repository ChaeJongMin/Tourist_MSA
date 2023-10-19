package com.review.reviewservice.Service;

import com.review.reviewservice.Dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    ResponseTouristReviewDto getTouristToReviews(String tourDestNm, String userId);
    List<ResponseReviewToUserDto> getReviewToUserDto(String userId);
    @Transactional
    Long save(RequestReviewDto requestReviewDto);

    @Transactional
    ResponseUpdateDto update(Long id, RequestUpdateDto requestUpdateDto);

    @Transactional
    Long delete(Long id);
}
