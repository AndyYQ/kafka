package com.qy;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProducerFastStart {
    public static final String brokerList = "192.168.245.128:9092,192.168.245.129:9092,192.168.245.130:9092";
    public static final String topic = "topic-demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        // 针对可重试异常，配置可重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 配置生产者拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerInterceptorPrefix.class.getName()+","+ProducerInterceptorPrefix02.class.getName());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello kafka");
        Future<RecordMetadata> future = kafkaProducer.send(record);
        try {
            RecordMetadata recordMetadata = future.get();
            System.out.println(recordMetadata.topic() + "-" + recordMetadata.partition() + "-" + recordMetadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ProducerRecord<String, String> record02 = new ProducerRecord<>(topic, "hello kafka02");
        kafkaProducer.send(record02,(metadata,exception) ->{
            if(exception!=null){
                exception.printStackTrace();
            }else{
                System.out.println(metadata.topic()+"-"+metadata.partition()+"-"+metadata.offset());
            }
        });
        kafkaProducer.close();
    }
}
