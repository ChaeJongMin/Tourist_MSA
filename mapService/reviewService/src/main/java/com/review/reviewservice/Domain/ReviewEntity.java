package com.review.reviewservice.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long touristId;
    private double score;
    private String reviewText;

    @Builder
    public ReviewEntity(Long userId, Long touristId, double score, String reviewText){
        this.userId=userId;
        this.touristId=touristId;
        this.score=score;
        this.reviewText=reviewText;
    }
}
