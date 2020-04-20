package com.qy;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class ProducerInterceptorPrefix implements ProducerInterceptor<String, String> {
    // 消息序列化和计算分区之前调用
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        String newValue = "prefix01 " + producerRecord.value();
        return new ProducerRecord<>(producerRecord.topic(), producerRecord.partition(), producerRecord.timestamp(), producerRecord.key(), newValue, producerRecord.headers());
    }

    // callBack之前调用或者get返回之前调用
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        // 异常不会向上传递，下一个拦截器会继续执行
        int num = 5 / 0;
        System.out.println("ProducerInterceptorPrefix onAcknowledgement");
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
