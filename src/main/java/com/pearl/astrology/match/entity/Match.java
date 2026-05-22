package com.pearl.astrology.match.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Document(collection = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    private String id;

    private String userId; // MongoDB ObjectId string

    private String candidateId; // MongoDB ObjectId string

    private double score;

    private LocalDateTime matchTimestamp;

    private boolean viewed = false;
}
