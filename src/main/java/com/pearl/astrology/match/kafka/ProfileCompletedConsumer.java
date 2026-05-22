// package com.pearl.astrology.match.kafka;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.pearl.astrology.match.entity.DailyMatchQueue;
// import com.pearl.astrology.match.repository.DailyMatchQueueRepository;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;
// import java.time.LocalDate;
// import java.util.Map;

// @Service
// @Slf4j
// @RequiredArgsConstructor
// public class ProfileCompletedConsumer {

//     private final DailyMatchQueueRepository queueRepository;
//     private final ObjectMapper objectMapper;

//     @KafkaListener(topics = "user-created", groupId = "match-engine-group")
//     public void consumeUserCreated(String message) {
//         log.info("Received user-created event: {}", message);

//         try {
//             // Manually parse JSON string to Map to handle Node.js JSON-in-String format
//             Map<String, Object> payload = objectMapper.readValue(message, Map.class);

//             // Priority: _id (Node.js), userId (Legacy), id (Generic)
//             String userId = null;
//             if (payload.containsKey("_id")) userId = payload.get("_id").toString();
//             else if (payload.containsKey("userId")) userId = payload.get("userId").toString();
//             else if (payload.containsKey("id")) userId = payload.get("id").toString();

//             if (userId == null) {
//                 log.warn("No valid ID found in user-created event payload: {}", payload);
//                 return;
//             }

//             // Avoid duplicate entries for the same day
//             boolean exists = queueRepository.existsByUserIdAndQueueDate(userId, LocalDate.now());

//             if (!exists) {
//                 DailyMatchQueue queueItem = DailyMatchQueue.builder()
//                         .userId(userId)
//                         .queueDate(LocalDate.now())
//                         .processed(false)
//                         .build();

//                 queueRepository.save(queueItem);
//                 log.info("User {} added to daily match queue", userId);
//             } else {
//                 log.warn("User {} already in queue for today", userId);
//             }
//         } catch (Exception e) {
//             log.error("Error parsing or processing user-created event: {}", message, e);
//         }
//     }
// }
