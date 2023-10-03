package com.review.reviewservice.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tourist_review")
public class TouristEntity {
    @Id
    private Long id;
    private String tourDestNm;

    @Builder
    public TouristEntity(Long id,String tourDestNm){
        this.id=id;
        this.tourDestNm=tourDestNm;
    }

}
