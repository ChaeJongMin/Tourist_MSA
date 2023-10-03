package com.mapinfo.mapservice.Dto;

import com.mapinfo.mapservice.Domain.TouristEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseTouristDto {
    private String tourDestNm;
    private Double lat;
    private Double lng	;
    private String availParkingCnt;
    private String capacity;
    private String tourDestIntro;
    private String addrRoad;
    private Long visitCnt;
    private String mngAgcTel;

    @Builder
    public ResponseTouristDto(TouristEntity touristEntity){
        this.tourDestNm=touristEntity.getTourDestNm();
        this.lat=touristEntity.getLat();
        this.lng=touristEntity.getLng();
        this.availParkingCnt=touristEntity.getAvailParkingCnt();
        this.capacity=touristEntity.getCapacity();
        this.tourDestIntro=touristEntity.getTourDestIntro();
        this.addrRoad=touristEntity.getAddrRoad();
        this.visitCnt=touristEntity.getVisitCnt();
        this.mngAgcTel=touristEntity.getMngAgcTel();
    }

}
