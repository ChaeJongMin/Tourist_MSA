package com.userinfo.userservice.MessageQueue;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.userinfo.userservice.Dto.Response.KafkaUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    public KafkaUserDto sendNewUser(String topic, KafkaUserDto userDto){
        String resultMsg=setMessage(userDto);
        kafkaTemplate.send(topic, resultMsg);
        log.info("Kafka Producer sendNewUser data from the User microservice: " + userDto);

        return userDto;
    }
    public KafkaUserDto sendUpdateUser(String topic, KafkaUserDto userDto) {
        String resultMsg=setMessage(userDto);
        kafkaTemplate.send(topic, resultMsg);
        log.info("Kafka Producer sendUpdateUser data from the User microservice: " + userDto);

        return userDto;
    }

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
