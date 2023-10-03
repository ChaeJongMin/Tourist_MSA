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
    private Double lng	;
    private String availParkingCnt;
    private String capacity;
    private String tourDestIntro;
    private String addrRoad;
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
