package com.review.reviewservice.Service;

import com.review.reviewservice.Dto.RequestReviewDto;
import com.review.reviewservice.Dto.RequestUpdateDto;
import com.review.reviewservice.Dto.ResponseReviewDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    List<ResponseReviewDto> getTouristToReviews(String tourDestNm);
    @Transactional
    Long save(RequestReviewDto requestReviewDto);

    @Transactional
    Long update(Long id, RequestUpdateDto requestUpdateDto);

    @Transactional
    Long delete(Long id);
}
