package com.pearl.astrology.match.batch;

import com.pearl.astrology.match.entity.DailyMatchQueue;
import com.pearl.astrology.match.entity.Match;
import com.pearl.astrology.match.repository.DailyMatchQueueRepository;
import com.pearl.astrology.match.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Persists match results to MongoDB and marks queue entries as processed.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MatchMongoWriter implements ItemWriter<List<Match>> {

    private final MatchRepository matchRepository;
    private final DailyMatchQueueRepository queueRepository;

    @Override
    public void write(Chunk<? extends List<Match>> chunk) {
        if (chunk == null || chunk.isEmpty()) return;

        // 1. Gather all non-empty match lists and user IDs in this chunk
        List<List<Match>> nonListMatches = chunk.getItems().stream()
                .filter(matches -> matches != null && !matches.isEmpty())
                .collect(Collectors.toList());

        if (nonListMatches.isEmpty()) return;

        List<String> userIds = nonListMatches.stream()
                .map(matches -> matches.get(0).getUserId())
                .distinct()
                .collect(Collectors.toList());

        log.info("Processing match updates in bulk for {} users", userIds.size());

        // 2. Bulk Delete old matches for all users in the chunk
        matchRepository.deleteByUserIdIn(userIds);

        // 3. Bulk Save all matches for the chunk
        List<Match> allMatches = nonListMatches.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        matchRepository.saveAll(allMatches);

        // 4. Bulk Update DailyMatchQueue statuses
        List<DailyMatchQueue> queueEntries = queueRepository.findByUserIdInAndQueueDateAndProcessedFalse(
                userIds, LocalDate.now()
        );
        for (DailyMatchQueue entry : queueEntries) {
            entry.setProcessed(true);
        }
        queueRepository.saveAll(queueEntries);

        log.info("Successfully saved {} total matches and updated queue for {} users", 
                allMatches.size(), userIds.size());
    }
}
