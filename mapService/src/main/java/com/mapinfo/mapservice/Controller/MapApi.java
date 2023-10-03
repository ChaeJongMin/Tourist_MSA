package com.mapinfo.mapservice.Controller;

import com.mapinfo.mapservice.Service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MapApi {
    private final MapService mapService;

    @GetMapping("/api/map")
    public ResponseEntity<?> getTouristInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(mapService.getTourist());
    }

    @GetMapping("/api/map/{touristNm}")
    public ResponseEntity<?> getSingleTouristInfo(@PathVariable String touristNm){
        log.info("/api/map/{touristNm} : "+touristNm);
        return ResponseEntity.status(HttpStatus.OK).body(mapService.getSingleTourist(touristNm));
    }


}
