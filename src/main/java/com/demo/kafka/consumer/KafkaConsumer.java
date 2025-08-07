package com.demo.kafka.consumer;

import com.demo.kafka.Employee;
import com.demo.kafka.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${topic.name.employee}")
    public void read(ConsumerRecord<String, Employee> consumerRecord) {
        String key = consumerRecord.key();
        Employee employee = consumerRecord.value();
        log.info("Avro message received for key " + key + " Value " + employee.toString());

    }

    @KafkaListener(topics = "${topic.name.student}")
    public void readStudent(ConsumerRecord<String, Student> consumerRecord) {
        String key = consumerRecord.key();
        Student student = consumerRecord.value();
        log.info("Avro message received for key  " + key + " Value " + student.toString());

    }


}