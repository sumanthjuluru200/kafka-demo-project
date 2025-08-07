package com.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${topic.name.employee}")
    private String employeeTopicName;

    @Value("${topic.name.student}")
    private String studentTopicName;


    @Bean
    public NewTopic CreateEmployeeTopic(){
        return TopicBuilder.name(employeeTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic createStudentTopic(){
        return TopicBuilder.name(studentTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
