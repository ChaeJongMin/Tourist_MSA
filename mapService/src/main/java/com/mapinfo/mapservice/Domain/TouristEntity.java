package com.mapinfo.mapservice.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TouristEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tourDestNm;
    private Double lat;
    private Double lng;
    // 주차 가능한 수
    private String availParkingCnt;
    // 수용량
    private String capacity;
    //관광지 정보
    private String tourDestIntro;
    //주소지
    private String addrRoad;
    //관리 번호
    private String mngAgcTel;
    private Long visitCnt;

    @Builder
    public TouristEntity(String name, Double lat, Double lng, String availParkingCnt, String capacity, String tourDestIntro,
                         String addrRoad, Long visitCnt, String mngAgcTel){
        this.tourDestNm = tourDestNm;
        this.lat = lat;
        this.lng = lng	;
        this.availParkingCnt = availParkingCnt;
        this.capacity=capacity;
        this.tourDestIntro=tourDestIntro;
        this.addrRoad=addrRoad;
        this.visitCnt=visitCnt;
        this.mngAgcTel=mngAgcTel;
    }
}
