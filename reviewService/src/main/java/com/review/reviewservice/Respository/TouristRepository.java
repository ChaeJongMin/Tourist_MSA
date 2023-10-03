package com.review.reviewservice.Respository;

import com.review.reviewservice.Domain.TouristEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristRepository extends JpaRepository<TouristEntity,Long> {

}
