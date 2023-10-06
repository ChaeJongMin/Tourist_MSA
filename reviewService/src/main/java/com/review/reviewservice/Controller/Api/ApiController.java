package com.review.reviewservice.Controller.Api;

import com.review.reviewservice.Dto.RequestReviewDto;
import com.review.reviewservice.Dto.RequestUpdateDto;
import com.review.reviewservice.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:9004")
@Slf4j
public class ApiController {
    private final ReviewService reviewService;

    @GetMapping("/api/review/{tourDestNm}")
    public ResponseEntity<?> getReview(@PathVariable String tourDestNm){
        log.info("Get 작동");
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getTouristToReviews(tourDestNm));
    }

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

    @DeleteMapping("/api/review/{id}")
    public ResponseEntity<?> deleteReview(@RequestBody RequestUpdateDto requestUpdateDto,
                                          @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.delete(id));
    }

}