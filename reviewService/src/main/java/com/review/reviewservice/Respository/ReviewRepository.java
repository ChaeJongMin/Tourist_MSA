package com.review.reviewservice.Respository;

import com.review.reviewservice.Domain.ReviewEntity;
import com.review.reviewservice.Dto.ResponseReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query("SELECT r FROM ReviewEntity r WHERE r.touristId = :id ORDER BY r.id DESC")
    List<ReviewEntity> findByTouristId(Long id);
    @Query("SELECT r FROM ReviewEntity r WHERE r.userId = :id ORDER BY r.id DESC")
    List<ReviewEntity> findByUserId(Long id);
}
