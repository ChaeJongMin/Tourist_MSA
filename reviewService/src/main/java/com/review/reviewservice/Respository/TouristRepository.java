package com.review.reviewservice.Respository;

import com.review.reviewservice.Domain.TouristEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TouristRepository extends JpaRepository<TouristEntity,Long>  {

    Optional<TouristEntity> findByTourDestNm(String search);
}
