package com.demo.kafka.producer;

import com.demo.kafka.Employee;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    @Value("${topic.name.employee}")
    @Setter
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplate;


    public void sendMessage(Employee employee) {
        if (employee == null) {
            System.err.println("Cannot send null employee to Kafka");
            return;
        }

        CompletableFuture<SendResult<String, Employee>> future =
                kafkaTemplate.send(topicName, UUID.randomUUID().toString(), employee);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message =[" + employee + "] with offset =[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message =[" + employee + "] due to : " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}
