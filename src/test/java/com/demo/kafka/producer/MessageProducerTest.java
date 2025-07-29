package com.demo.kafka.producer;

import com.demo.kafka.dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageProducerTest {

    @Mock
    private KafkaTemplate<String, Employee> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;


    @BeforeEach
    void setup() {
        kafkaProducer.setTopicName("test-topic");
    }

    @Test
    void testSendMessageSuccess() {
        Employee employee = new Employee("123", "sumanth", "juluru", "sumanth@gmail.com", "28/06/1994", "30");
        CompletableFuture<SendResult<String, Employee>> future = new CompletableFuture<>();
        // We can complete the future with null because the success path doesn't use the SendResult
        future.complete(null);

        when(kafkaTemplate.send(anyString(), anyString(), any(Employee.class))).thenReturn(future);

        // Act
        kafkaProducer.sendMessage(employee);

        // Assert
        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), eq(employee));
    }

    @Test
    void testSendMessage_NullEmployee() {
        // Act
        kafkaProducer.sendMessage(null);

        // Assert
        // Verify that kafkaTemplate.send is never called when the employee is null
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any());
    }

    @Test
    void testSendMessage_Failure() {
        // Arrange
        Employee employee = new Employee("123", "sumanth", "juluru", "sumanth@gmail.com", "28/06/1994", "30");
        CompletableFuture<SendResult<String, Employee>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka is down"));

        when(kafkaTemplate.send(anyString(), anyString(), any(Employee.class))).thenReturn(future);

        // Act
        kafkaProducer.sendMessage(employee);

        // Assert
        // Verify that the send method was still called once
        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), eq(employee));
        // You would typically also verify that an error was logged, but for this example,
        // we just confirm the interaction happened.
    }


}
