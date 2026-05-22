package com.pearl.astrology.match.batch;

import com.pearl.astrology.match.entity.UserProfile;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserCandidatePool {
    private String userId; // MongoDB ObjectId string
    private UserProfile sourceProfile; // Source user's full profile for scoring
    private List<UserProfile> candidates; // Full candidate profiles for scoring
}
