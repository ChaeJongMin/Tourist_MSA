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
//관광지에 대한 간단한 정보를 저장해놓은 tourist_review 테이블의 데이터와 매핑하는 엔티티
//이미 관광지에 대한 테이블이 존재하지만 리뷰 인스턴스에서 맵 인스턴스와 최소한의 통신을 위한 별도로 생성
//UserEntity도 같은 이유로 생성
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
