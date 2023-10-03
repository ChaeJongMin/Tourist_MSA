package com.mapinfo.mapservice.Repository;

import com.mapinfo.mapservice.Domain.TouristEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TouristRepository extends JpaRepository<TouristEntity, Long> {

    Optional<TouristEntity> findByTourDestNm(String keyward);
}
