package com.userinfo.userservice.Client;

import com.userinfo.userservice.Dto.Response.ResponseReviewToUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//FeiginClient로 review 인스턴스의 API 요청하기 위한 인터페이스
@FeignClient(name="review-service")
public interface ReviewServiceClient {
    //마이페이지에 자신이 쓴 리뷰 리스트를 얻기 위한 API
    @GetMapping("/review-service/api/review/{userId}")
    List<ResponseReviewToUserDto> getReviews(@PathVariable String userId);
}
