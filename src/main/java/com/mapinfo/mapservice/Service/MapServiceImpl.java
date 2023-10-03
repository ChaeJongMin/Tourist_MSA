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

    @Override
    public List<ResponseTouristDto> getTourist() {
        List<TouristEntity> touristEntityList = touristRepository.findAll();
        return touristEntityList.stream()
                .map(ResponseTouristDto::new).toList();

    }

    @Override
    public ResponseTouristDto getSingleTourist(String touristNm){
        Optional<TouristEntity> touristEntity=touristRepository.findByTourDestNm(touristNm);
        if(touristEntity.isEmpty()){
            throw new RuntimeException();
        }
        return new ResponseTouristDto(touristEntity.get());
    }
}
