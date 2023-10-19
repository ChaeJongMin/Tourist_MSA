package com.mapinfo.mapservice.Controller;

import com.mapinfo.mapservice.Service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map-service")
@Slf4j
public class MapApi {
    private final MapService mapService;

    @GetMapping("/api/map")
    public ResponseEntity<?> getTouristInfo(){
        log.info("");
        return ResponseEntity.status(HttpStatus.OK).body(mapService.getTourist());
    }

    @GetMapping("/api/map/{touristNm}")
    public ResponseEntity<?> getSingleTouristInfo(@PathVariable String touristNm){
        log.info("/api/map/{touristNm} : "+touristNm);
        return ResponseEntity.status(HttpStatus.OK).body(mapService.getSingleTourist(touristNm));
    }


}
