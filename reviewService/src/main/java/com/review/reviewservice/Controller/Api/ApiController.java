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

    @CrossOrigin("*")
    @GetMapping("/api/{tourDestNm}/review/{userId}")
    public ResponseEntity<?> getReview(@PathVariable String tourDestNm, @PathVariable String userId){
        log.info("Get 작동: "+ tourDestNm +" "+userId);
        ResponseTouristReviewDto reviewDto = reviewService.getTouristToReviews(tourDestNm, userId);
        if(reviewDto.getIsReviewed()){
            log.info("isReviewed 참");
        } else {
            log.info("isReviewed 거짓");
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
    }
    @GetMapping("/api/review/{userId}")
    public ResponseEntity<List<ResponseReviewToUserDto>> getReviewToUser(@PathVariable String userId){
        log.info("/api/review/{userId} 호출!!");
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewToUserDto(userId));
    }
    @CrossOrigin("*")
    @PostMapping("/api/review")
    public ResponseEntity<?> saveReview(@RequestBody RequestReviewDto requestReviewDto){
        log.info("Post 작동 : "+ requestReviewDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.save(requestReviewDto));
    }

    @PutMapping("/api/review/{id}")
    public ResponseEntity<?> updateReview(@RequestBody RequestUpdateDto requestUpdateDto,
                                          @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.update(id, requestUpdateDto));
    }

    @CrossOrigin("*")
    @DeleteMapping("/api/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.delete(id));
    }

}