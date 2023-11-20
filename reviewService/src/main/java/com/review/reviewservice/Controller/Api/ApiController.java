package com.review.reviewservice.Controller.Api;

import com.review.reviewservice.Dto.*;
import com.review.reviewservice.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review-service")
@Slf4j
public class ApiController {
    private final ReviewService reviewService;
    //해당 관광지에 대한 리뷰 리스트를 응답하는 API
    //userId가 필요한 이유는 자신(유저)이 쓴 리뷰가 있는지 확인하기 위해서 사용
    //만약 자신의 리뷰가 있다면 클라이언트측에서 최상단에 자신의 리뷰 노출
    @GetMapping("/api/{tourDestNm}/review/{userId}")
    public ResponseEntity<?> getReview(@PathVariable String tourDestNm, @PathVariable String userId){
        log.info("Get 작동: "+ tourDestNm +" "+userId);
        ResponseTouristReviewDto reviewDto = reviewService.getTouristToReviews(tourDestNm, userId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
    }

    // 마이페이지에서 자신이 쓴 리뷰 리스트를 응답하는 API
    @GetMapping("/api/review/{userId}")
    public ResponseEntity<List<ResponseReviewToUserDto>> getReviewToUser(@PathVariable String userId){
        log.info("/api/review/{userId} 호출!!");
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewToUserDto(userId));
    }
    // 리뷰를 등록하는 API
    @PostMapping("/api/review")
    public ResponseEntity<?> saveReview(@RequestBody RequestReviewDto requestReviewDto){
        log.info("Post 작동 : "+ requestReviewDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.save(requestReviewDto));
    }

    //리뷰를 수정하는 API
    @PutMapping("/api/review/{id}")
    public ResponseEntity<?> updateReview(@RequestBody RequestUpdateDto requestUpdateDto,
                                          @PathVariable Long id){
        log.info("Put /api/review/{id} 작동 : "+requestUpdateDto.getScore()+" "+requestUpdateDto.getReviewTexts());
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.update(id, requestUpdateDto));
    }

    //리뷰를 삭제하는 API
    @DeleteMapping("/api/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){
        log.info("Delete /api/review/{id} 작동");
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.delete(id));
    }

}