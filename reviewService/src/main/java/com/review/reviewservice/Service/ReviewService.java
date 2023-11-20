package com.review.reviewservice.Service;

import com.review.reviewservice.Dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    //관광지에 대한 리뷰를 반환하는 메소드
    ResponseTouristReviewDto getTouristToReviews(String tourDestNm, String userId);
    //유저가 쓴 리뷰를 반환하는 메소드
    List<ResponseReviewToUserDto> getReviewToUserDto(String userId);
    //리뷰를 저장하는 메소드
    @Transactional
    Long save(RequestReviewDto requestReviewDto);
    //리뷰를 수정하는 메소드
    @Transactional
    ResponseUpdateDto update(Long id, RequestUpdateDto requestUpdateDto);
    //리뷰를 삭제하는 메소드
    @Transactional
    Long delete(Long id);
}
