package com.pearl.astrology.match.repository;

import com.pearl.astrology.match.entity.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MatchRepository extends MongoRepository<Match, String> {
    List<Match> findByUserIdOrderByScoreDesc(String userId);
    void deleteByUserId(String userId);
    void deleteByUserIdIn(List<String> userIds);
}
