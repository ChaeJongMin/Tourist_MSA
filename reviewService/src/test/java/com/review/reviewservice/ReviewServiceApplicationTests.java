package com.review.reviewservice;

import com.review.reviewservice.Domain.UserEntity;
import com.review.reviewservice.Respository.ReviewRepository;
import com.review.reviewservice.Respository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewServiceApplicationTests {
	@Autowired
	private ReviewRepository touristRepository;
	@Autowired
	private UserRepository userRepository;
	@Test
	void contextLoads() {
		UserEntity user = UserEntity.builder()
				.email("test@test.com")
				.name("test임")
				.id(8L)
				.build();
		userRepository.save(user);


	}



}
