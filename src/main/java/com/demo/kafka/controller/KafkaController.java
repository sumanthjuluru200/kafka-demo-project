package com.demo.kafka.controller;

import com.demo.kafka.Employee;
import com.demo.kafka.Student;
import com.demo.kafka.Student;
import com.demo.kafka.producer.KafkaProducer;
import com.demo.kafka.producer.StudentKafkaProduer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private StudentKafkaProduer studentKafkaProduer;

    @PostMapping("/events")
    public String sendMessage(@RequestBody Employee employee) {
        kafkaProducer.sendMessage(employee);
        return "message published!" + employee;
    }


    @PostMapping("/students")
    public String sendMessage(@RequestBody Student student) {
        studentKafkaProduer.sendMessage(student);
        return "message published!" + student;
    }


}
