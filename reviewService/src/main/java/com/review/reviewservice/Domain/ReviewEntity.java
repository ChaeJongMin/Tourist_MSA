package com.review.reviewservice.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ReviewEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long touristId;
    private double score;
    @Column(columnDefinition = "text")
    private String reviewText;
    private boolean isUpdate = false;
    @Builder
    public ReviewEntity(Long userId, Long touristId, double score, String reviewText, boolean isUpdate){
        this.userId=userId;
        this.touristId=touristId;
        this.score=score;
        this.reviewText=reviewText;
        this.isUpdate=isUpdate;
    }

    public void update(double score, String reviewText){
        this.score=score;
        this.reviewText=reviewText;
        isUpdate=true;
    }
}
