package com.userinfo.userservice.Service;

import com.userinfo.userservice.Client.ReviewServiceClient;
import com.userinfo.userservice.Domain.UserEntity;
import com.userinfo.userservice.Dto.Request.SaveDto;
import com.userinfo.userservice.Dto.Request.UpdateDto;
import com.userinfo.userservice.Dto.Response.KafkaUserDto;
import com.userinfo.userservice.Dto.Response.ResponseReviewToUserDto;
import com.userinfo.userservice.Dto.Response.UserDto;
import com.userinfo.userservice.MessageQueue.KafkaProducer;
import com.userinfo.userservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ReviewServiceClient reviewServiceClient;

    private final KafkaProducer kafkaProducer;

    @Override
    public Long save(SaveDto saveDto) {
        if(!userRepository.existsByEmail(saveDto.getEmail())){
            saveDto.setPasswd(passwordEncoder.encode(saveDto.getPasswd()));
            log.info("회원가입 성공!!!");
            UserEntity userEntity = userRepository.save(saveDto.toUserEntity());

            //Kafka 메시지 send!!
            kafkaProducer.sendNewUser("review-createUser-topic",new KafkaUserDto(userEntity));

            return userEntity.getId();
        }
        log.info("save 메소드 예외 발생");
        throw new DuplicateKeyException("이미 이메일이 존재합니다.");

    }

    @Override
    public UserDto getUserByUserId(String userId) {
        //FeginClient로 리뷰 리스트 받아오기
        log.info("FeginClient으로 통신 시작");
        List<ResponseReviewToUserDto> reviewList = reviewServiceClient.getReviews(userId);

        UserEntity userEntity =userRepository.findByName(userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
        return new UserDto(userEntity,reviewList);
    }

    @Override
    public Long update(String userId, UpdateDto updateDto) {
        UserEntity userEntity =userRepository.findByName(userId)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
        userEntity.update(updateDto.getEmail(), updateDto.getPhone());

        KafkaUserDto kafkaUserDto = new KafkaUserDto(userEntity);

        //Kafka 메시지 send!!
        kafkaProducer.sendUpdateUser("review-updateUser-topic",kafkaUserDto);
        return userEntity.getId();
    }

    @Override
    public String getUserNameByEmail(String email) {
        UserEntity userEntity =userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
        return userEntity.getName();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity =userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("해당 유저는 없습니다."));
        return new User(userEntity.getEmail(), userEntity.getPasswd(), true, true, true, true,
                new ArrayList<>());
    }
}
