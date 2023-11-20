package com.mapinfo.mapservice.Service;

import com.mapinfo.mapservice.Domain.TouristEntity;
import com.mapinfo.mapservice.Dto.ResponseTouristDto;
import com.mapinfo.mapservice.Repository.TouristRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MapServiceImpl implements MapService{

    private final TouristRepository touristRepository;

    //관광지 엔티티를 DTO 리스트 형태로 반환하는 메소드 (모든 관광지)
    @Override
    public List<ResponseTouristDto> getTourist() {
        List<TouristEntity> touristEntityList = touristRepository.findAll();
        return touristEntityList.stream()
                .map(ResponseTouristDto::new).toList();

    }

    // 특정 단일 관광 엔티티를 얻어 DTO 객체를 반환하는 메소드
    @Override
    public ResponseTouristDto getSingleTourist(String touristNm){
        //특정 관광지를 알기 위해 관광지명을 사용
        Optional<TouristEntity> touristEntity=touristRepository.findByTourDestNm(touristNm);
        if(touristEntity.isEmpty()){
            throw new RuntimeException();
        }
        return new ResponseTouristDto(touristEntity.get());
    }
}
