package com.demo.kafka.producer;

import com.demo.kafka.Student;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentKafkaProduer {


    @Value("${topic.name.student}")
    @Setter
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Student> kafkaTemplate;


    public void sendMessage(Student student) {
        if (student == null) {
            System.err.println("Cannot send null student to Kafka");
            return;
        }

        CompletableFuture<SendResult<String, Student>> future =
                kafkaTemplate.send(topicName, UUID.randomUUID().toString(), student);

        future.whenComplete((result, throwable) -> {
            if (throwable == null) {
                System.out.println("Sent message =[" + student + "] with offset =[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message =[" + student + "] due to : " + throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }
}
