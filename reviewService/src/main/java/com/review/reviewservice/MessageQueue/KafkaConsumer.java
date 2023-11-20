package com.review.reviewservice.MessageQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.review.reviewservice.Domain.UserEntity;
import com.review.reviewservice.Respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
//Kafka Producer로부터 보낸 메시지를 받는 consumer
public class KafkaConsumer {
    private final UserRepository userRepository;
    private Map<Object, Object> map;
    private final Gson gson ;
    @Transactional
    //특정 토픽 이름을 설정
    // "review-createUser-topic" 토픽에 전달된 메시지를 받아 처리 (user 인스턴스가 회원가입 시 Broker에 메시지 전달)
    @KafkaListener(topics = "review-createUser-topic")
    public void createUser(String kafkaMessage) {
        log.info("Kafka Message for create : -> " + kafkaMessage);

        map = new HashMap<>();
        //받아온 메시지 페이로드에 존재하는 데이터를 map 형태로 변경
        try {
            //Gson 라이브러리를 사용하여 kafkaMessage(직렬화) 메시지를 Java의 Map객체로 변환
            map = gson.fromJson(kafkaMessage , new TypeToken<Map<Object, Object>>() {}.getType());
            //받아온 정보로 User 엔티티 생성
            UserEntity entity = UserEntity.builder().
                    id (((Number) map.get("id")).longValue()).
                    name((String) map.get("name")).
                    email((String) map.get("email")).
                    build();
            // 저장
            userRepository.save(entity);

        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
        }


    }

    @Transactional
    //특정 토픽 이름을 설정
    // "review-updateUser-topic" 토픽에 전달된 메시지를 받아 처리 (user 인스턴스가 정보 수정시 Broker에 메시지 전달)
    @KafkaListener(topics = "review-updateUser-topic")
    public void updateUser(String kafkaMessage) {
        log.info("Kafka Message for update : -> " + kafkaMessage);

        map = new HashMap<>();
        //받아온 메시지 페이로드에 존재하는 데이터를 map 형태로 변경
        try {
            map = gson.fromJson(kafkaMessage , new TypeToken<Map<Object, Object>>() {}.getType());
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
        }
        // 받아온 정보 중 'Id'로 유저 엔티티 찾기
        UserEntity user = userRepository.findById(((Number) map.get("id")).longValue()).orElseThrow(
                () -> new IllegalArgumentException("해다 유저는 없습니다."));
        // 수정
        user.update((String) map.get("name"), (String) map.get("email"));

    }

}