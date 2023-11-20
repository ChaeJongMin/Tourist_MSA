package com.userinfo.userservice.MessageQueue;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.userinfo.userservice.Dto.Response.KafkaUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//카프카로부터 메시지를 보내기 위한 클래스
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    //회원가입시 KafkaUserDto객체를 review 인스턴스에 메시지를 보내는 메소드
    public KafkaUserDto sendNewUser(String topic, KafkaUserDto userDto){
        String resultMsg=setMessage(userDto);
        //kafkaTemplate를 통해 broker에 전달
        kafkaTemplate.send(topic, resultMsg);
        log.info("Kafka Producer sendNewUser data from the User microservice: " + userDto);

        return userDto;
    }
    //유저 정부를 수정 시 KafkaUserDto객체를 review 인스턴스에 메시지를 보내는 메소드
    public KafkaUserDto sendUpdateUser(String topic, KafkaUserDto userDto) {
        String resultMsg=setMessage(userDto);
        //kafkaTemplate를 통해 broker에 전달
        kafkaTemplate.send(topic, resultMsg);
        log.info("Kafka Producer sendUpdateUser data from the User microservice: " + userDto);

        return userDto;
    }
    //KafkaUserDto 객체를 json 스트링 형태로 변환
    public String setMessage(KafkaUserDto userDto){
        log.info("보낼 객체 타입 확인 -> id: "+userDto.getId().getClass().getName() +" email : "+userDto.getEmail().getClass().getName()
                +" name : "+ userDto.getName().getClass().getName());
        String jsonInString ="";
        try {
            jsonInString = gson.toJson(userDto);
        } catch (JsonSyntaxException ex){
            ex.printStackTrace();
        }
        return jsonInString;
    }
}
