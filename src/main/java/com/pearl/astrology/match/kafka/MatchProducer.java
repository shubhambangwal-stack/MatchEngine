package com.pearl.astrology.match.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "match.daily_batch_ready";

    public void publishBatchReady(int totalUsersProcessed) {
        log.info("Publishing {} event for {} users", TOPIC, totalUsersProcessed);
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("batchDate", LocalDate.now().toString());
        payload.put("totalUsers", totalUsersProcessed);
        payload.put("status", "SUCCESS");
        
        kafkaTemplate.send(TOPIC, payload);
    }
}
