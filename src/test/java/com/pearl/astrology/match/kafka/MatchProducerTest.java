package com.pearl.astrology.match.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private MatchProducer matchProducer;

    @Test
    public void testPublishBatchReady() {
        int totalUsers = 123;
        matchProducer.publishBatchReady(totalUsers);

        verify(kafkaTemplate, times(1)).send(eq("match.daily_batch_ready"), argThat(argument -> {
            if (!(argument instanceof Map)) {
                return false;
            }
            Map<?, ?> payload = (Map<?, ?>) argument;
            return LocalDate.now().toString().equals(payload.get("batchDate")) &&
                   Integer.valueOf(totalUsers).equals(payload.get("totalUsers")) &&
                   "SUCCESS".equals(payload.get("status"));
        }));
    }
}
