package com.review.reviewservice.Service;

import com.review.reviewservice.Domain.ReviewEntity;
import com.review.reviewservice.Domain.TouristEntity;
import com.review.reviewservice.Domain.UserEntity;
import com.review.reviewservice.Dto.RequestReviewDto;
import com.review.reviewservice.Dto.RequestUpdateDto;
import com.review.reviewservice.Dto.ResponseReviewDto;
import com.review.reviewservice.Respository.ReviewRepository;
import com.review.reviewservice.Respository.TouristRepository;
import com.review.reviewservice.Respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final UserRepository userRepository;
    private final TouristRepository touristRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Long save(RequestReviewDto requestReviewDto) {
        TouristEntity tourist = touristRepository.findByTourDestNm(requestReviewDto.getTourDestNm())
                .orElseThrow(() -> new IllegalArgumentException("해당 관광지는 존재하지 않습니다."));

        UserEntity user = userRepository.findByName(requestReviewDto.getUserNm())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
        ReviewEntity reviewEntity = requestReviewDto.toReviewEntity(user.getId(), tourist.getId());
        reviewRepository.save(reviewEntity);
        return reviewEntity.getId();
    }

    @Override
    public List<ResponseReviewDto> getTouristToReviews(String tourDestNm){
        TouristEntity tourist = touristRepository.findByTourDestNm(tourDestNm)
                .orElseThrow(() -> new IllegalArgumentException("해당 관광지는 존재하지 않습니다."));
        Long tourId = tourist.getId();

        return reviewRepository.findByTouristId(tourId).stream()
                .map(review -> {
                    LocalDateTime dateToUse = review.isUpdate() ? review.getUpdateDates() : review.getCreateDates();

                    // 리뷰와 관련된 사용자 정보를 한 번에 가져옵니다.
                    UserEntity user = userRepository.findById(review.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 관광지는 존재하지 않습니다."));

                    // ResponseReviewDto를 생성하고 사용자 이름을 포함하여 반환합니다.
                    return new ResponseReviewDto(review, convertDate(dateToUse), user.getName());
                })
                .toList();
}

    public String convertDate(LocalDateTime localDateTime){
        // 한글로 요일 표시를 위해 Locale을 한국으로 설정합니다.
        Locale koreanLocale = Locale.KOREAN;
        // 사용할 포맷을 정의합니다. "요일" 부분을 "E"로 설정하여 요일의 약자를 얻습니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd E", koreanLocale);
        // 변환된 문자열을 반환합니다.
        return localDateTime.format(formatter);
    }

    @Override
    public Long update(Long id, RequestUpdateDto requestUpdateDto) {
        ReviewEntity review=reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("수정할 리뷰는 없습니다. id=" + id));
        review.update(requestUpdateDto.getScore(), requestUpdateDto.getReviewTexts());
        return review.getId();
    }

    @Override
    public Long delete(Long id) {
        ReviewEntity review=reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("삭제할 리뷰는 없습니다. id=" + id));

        //리뷰 점수 수정을 위해 Tourist와 데이터 동기화 필요!!

        reviewRepository.delete(review);
        return id;
    }

}
