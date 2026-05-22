package com.pearl.astrology.match.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Document(collection = "daily_match_queue")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMatchQueue {

    @Id
    private String id;

    private String userId; // MongoDB ObjectId string

    private LocalDate queueDate;

    @Builder.Default
    private boolean processed = false;

    private int retryCount = 0;
}
