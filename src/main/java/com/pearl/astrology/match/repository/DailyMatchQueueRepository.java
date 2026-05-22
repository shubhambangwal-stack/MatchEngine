package com.pearl.astrology.match.repository;

import com.pearl.astrology.match.entity.DailyMatchQueue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface DailyMatchQueueRepository extends MongoRepository<DailyMatchQueue, String> {
    boolean existsByUserIdAndQueueDate(String userId, LocalDate queueDate);
    Page<DailyMatchQueue> findByQueueDateAndProcessedFalse(LocalDate queueDate, Pageable pageable);
    List<DailyMatchQueue> findByQueueDateAndProcessedFalse(LocalDate queueDate);
    List<DailyMatchQueue> findByUserIdInAndQueueDateAndProcessedFalse(List<String> userIds, LocalDate queueDate);
}
