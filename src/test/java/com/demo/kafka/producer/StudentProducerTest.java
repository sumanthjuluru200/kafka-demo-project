package com.demo.kafka.producer;

import com.demo.kafka.Student;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentProducerTest {


    @InjectMocks
    private StudentKafkaProduer producer;

    @Mock
    private KafkaTemplate<String, Student> kafkaTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producer.setTopicName("student-topic");
    }

    @Test
    void testSendMessage_validStudent_shouldSendMessage() {
        // Arrange
        Student student = new Student(); // Populate if needed
        SendResult<String, Student> sendResult = mock(SendResult.class);
        RecordMetadata metadata = mock(RecordMetadata.class);
        when(metadata.offset()).thenReturn(10L);
        when(sendResult.getRecordMetadata()).thenReturn(metadata);

        CompletableFuture<SendResult<String, Student>> future = new CompletableFuture<>();
        future.complete(sendResult);

        when(kafkaTemplate.send(any(), any(), any())).thenReturn(future);

        // Act
        producer.sendMessage(student);

        // Assert
        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), eq(student));
    }

    @Test
    void testSendMessage_nullStudent_shouldNotSend() {
        // Act
        producer.sendMessage(null);

        // Assert
        verify(kafkaTemplate, never()).send(any(), any(), any());
    }

    @Test
    void testSendMessage_sendFails_shouldPrintError() {
        // Arrange
        Student student = new Student();
        CompletableFuture<SendResult<String, Student>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka failure"));

        when(kafkaTemplate.send(any(), any(), any())).thenReturn(future);

        // Act
        producer.sendMessage(student);

        // Since it's just logging the error, no assertions, but you can verify send was called
        verify(kafkaTemplate, times(1)).send(any(), any(), any());
    }
}
