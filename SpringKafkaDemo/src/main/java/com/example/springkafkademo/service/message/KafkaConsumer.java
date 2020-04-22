package com.example.springkafkademo.service.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"topic-demo"},groupId = "group.demo")
    public void listen(ConsumerRecord<String,String> record){
        System.out.println(record.topic()+","+record.partition()+","+record.value());
    }

}
