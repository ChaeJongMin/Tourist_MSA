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
public class KafkaConsumer {
    private final UserRepository userRepository;
    private Map<Object, Object> map;
    private final Gson gson ;
    @Transactional
    //특정 토픽 이름을 설정
    //해당 토픽에 메시지가 오면 CATCH(해당 메소드가 실행)
    @KafkaListener(topics = "review-createUser-topic")
    public void createUser(String kafkaMessage) {
        log.info("Kafka Message for create : -> " + kafkaMessage);

        map = new HashMap<>();
        //받아온 메시지 페이로드에 존재하는 데이터를 map 형태로 변경
        try {
            map = gson.fromJson(kafkaMessage , new TypeToken<Map<Object, Object>>() {}.getType());
            UserEntity entity = UserEntity.builder().
                    id (((Number) map.get("id")).longValue()).
                    name((String) map.get("name")).
                    email((String) map.get("email")).
                    build();
            userRepository.save(entity);

        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
        }


    }

    @Transactional
    //특정 토픽 이름을 설정
    //해당 토픽에 메시지가 오면 CATCH(해당 메소드가 실행)
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

        UserEntity user = userRepository.findById(((Number) map.get("id")).longValue()).orElseThrow(
                () -> new IllegalArgumentException("해다 유저는 없습니다."));
        user.update((String) map.get("name"), (String) map.get("email"));

    }

}