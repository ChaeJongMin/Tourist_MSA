package com.review.reviewservice.Respository;

import com.review.reviewservice.Domain.ReviewEntity;
import com.review.reviewservice.Dto.ResponseReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByTouristId(Long id);
}
