package com.demo.kafka.controller;

import com.demo.kafka.dto.Employee;
import com.demo.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    public String sendmessage(@RequestBody Employee employee){
        kafkaProducer.sendmessage(employee);
        return "message published!";
    }
}
