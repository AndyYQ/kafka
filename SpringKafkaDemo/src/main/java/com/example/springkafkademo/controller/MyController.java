package com.example.springkafkademo.controller;

import com.example.springkafkademo.service.message.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @ResponseBody
    @RequestMapping("/test")
    public String hello() {
        kafkaProducer.send("这是一个测试的消息");
        return "hello world";
    }
}
