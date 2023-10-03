package com.mapinfo.mapservice.Service;

import com.mapinfo.mapservice.Dto.ResponseTouristDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MapService{

    List<ResponseTouristDto> getTourist();

    ResponseTouristDto getSingleTourist(String touristNm);
}
