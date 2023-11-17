package com.userinfo.userservice;

import com.userinfo.userservice.Domain.UserEntity;
import com.userinfo.userservice.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest

class UserServiceApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
		UserEntity userEntity = userRepository.findByEmail("dico2760@test.com")
				.orElseThrow(() -> new IllegalArgumentException("실패"));
		userEntity.setPasswd(passwordEncoder.encode("cgs2760"));
		userRepository.save(userEntity);
	}

}
