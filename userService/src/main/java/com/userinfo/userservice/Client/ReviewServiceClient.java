package com.userinfo.userservice.Client;

import com.userinfo.userservice.Dto.Response.ResponseReviewToUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="review-service")
public interface ReviewServiceClient {
    @GetMapping("/review-service/api/review/{userId}")
    List<ResponseReviewToUserDto> getReviews(@PathVariable String userId);
}
