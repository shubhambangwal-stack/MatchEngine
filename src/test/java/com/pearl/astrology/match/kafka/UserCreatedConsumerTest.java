package com.pearl.astrology.match.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pearl.astrology.match.entity.DailyMatchQueue;
import com.pearl.astrology.match.repository.DailyMatchQueueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserCreatedConsumerTest {

    @Mock
    private DailyMatchQueueRepository queueRepository;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private UserCreatedConsumer consumer;

    @Test
    public void testConsumeUserCreatedSuccessfully() {
        String rawMessage = "{\"_id\":\"user_abc\",\"gender\":\"Male\"}";
        when(queueRepository.existsByUserIdAndQueueDate("user_abc", LocalDate.now())).thenReturn(false);

        consumer.consumeUserCreated(rawMessage);

        verify(queueRepository, times(1)).save(argThat(item -> 
            "user_abc".equals(item.getUserId()) && !item.isProcessed()
        ));
    }

    @Test
    public void testConsumeDoubleStringifiedJsonSuccessfully() {
        String rawMessage = "\"{\\\"_id\\\":\\\"user_abc\\\",\\\"gender\\\":\\\"Male\\\"}\"";
        when(queueRepository.existsByUserIdAndQueueDate("user_abc", LocalDate.now())).thenReturn(false);

        consumer.consumeUserCreated(rawMessage);

        verify(queueRepository, times(1)).save(argThat(item -> 
            "user_abc".equals(item.getUserId()) && !item.isProcessed()
        ));
    }

    @Test
    public void testConsumeDuplicateDoesNotSave() {
        String rawMessage = "{\"_id\":\"user_abc\",\"gender\":\"Male\"}";
        when(queueRepository.existsByUserIdAndQueueDate("user_abc", LocalDate.now())).thenReturn(true);

        consumer.consumeUserCreated(rawMessage);

        verify(queueRepository, never()).save(any(DailyMatchQueue.class));
    }

    @Test
    public void testConsumeInvalidJsonThrowsException() {
        String rawMessage = "invalid-json-content";

        assertThrows(RuntimeException.class, () -> {
            consumer.consumeUserCreated(rawMessage);
        });

        verify(queueRepository, never()).save(any(DailyMatchQueue.class));
    }

    @Test
    public void testConsumeMissingIdThrowsException() {
        String rawMessage = "{\"gender\":\"Male\"}";

        assertThrows(RuntimeException.class, () -> {
            consumer.consumeUserCreated(rawMessage);
        });

        verify(queueRepository, never()).save(any(DailyMatchQueue.class));
    }
}
