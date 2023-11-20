package com.review.reviewservice.Config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
//카프카(Consumer)에 대한 환경정보를 관리하는 클래스
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String,Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");
        // Kafka에서 받아온 메시지는 JSON 형식으로 <KEY, VALUE> 형식
        // Producer에서 보낼 메시지를 직렬화로 압축하여 보내지는데 받아온 메시지는 역직렬화로 디코딩하여 값을 얻어온다.
        // StringDeserializer.class 를 통해 key 와 value를 String 값으로 받아온다.
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    //토픽에 변경사항이 있는지 지속적으로 이벤트 발생시 Catch 할 수 있는 리스너 등록
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        //  Kafka 토픽에서 메시지를 수신하고 처리하는 클래스
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();

        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        return kafkaListenerContainerFactory;
    }
}
