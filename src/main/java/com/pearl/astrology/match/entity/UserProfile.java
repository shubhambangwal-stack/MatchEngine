package com.pearl.astrology.match.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

/**
 * Read-only mapping of the User document created by the Node.js backend in MongoDB.
 * This entity is used for candidate fetching and compatibility scoring.
 */
@Document(collection = "users")
@CompoundIndex(name = "match_candidate_search_idx", def = "{'isActive': 1, 'isBlocked': 1, 'profileCompleted': 1, 'gender': 1, 'dob': 1, 'religion': 1, 'motherTongue': 1, 'maritalStatus': 1}")
@Data
@NoArgsConstructor
public class UserProfile {

    @Id
    private String id; // MongoDB ObjectId

    private String fullName;
    private String gender;
    private LocalDate dob;
    private String religion;
    private String motherTongue;
    private String maritalStatus;

    // Professional
    private String highestQualification;
    private String college;
    private String workingWith;
    private String profession;
    private String annualIncome;

    // Family
    private String familyStatus;
    private String familyType;
    private String familyValues;
    private String fatherOccupation;
    private String motherOccupation;
    private String aboutFamily;

    // Personality
    private String aboutMe;
    private List<String> values;
    private String lifeGoals;
    private List<String> hobbies;

    // Lifestyle
    private Lifestyle lifestyle;

    // Partner Preference
    private PartnerPreference partnerPreference;

    // Horoscope
    private Horoscope horoscope;

    // Profile Status
    private boolean profileCompleted;
    private int profileCompletionPercentage;
    private boolean isActive;
    private boolean isBlocked;
    private String subscriptionTier; // e.g., "FREE", "GOLD", "DIAMOND"

    // --- Nested subdocument classes ---

    @Data
    @NoArgsConstructor
    public static class Lifestyle {
        private String diet;
        private String smoking;
        private String drinking;
        private List<String> interests;
    }

    @Data
    @NoArgsConstructor
    public static class PartnerPreference {
        private AgeRange ageRange;
        private HeightRange heightRange;
        private List<String> maritalStatus;
        private List<String> religion;
        private List<String> motherTongue;
    }

    @Data
    @NoArgsConstructor
    public static class AgeRange {
        private int min;
        private int max;
    }

    @Data
    @NoArgsConstructor
    public static class HeightRange {
        private String min;
        private String max;
    }

    @Data
    @NoArgsConstructor
    public static class Horoscope {
        private LocalDate dateOfBirth;
        private String timeOfBirth;
        private String cityOfBirth;
        private String gotra;
        private String manglik;
    }
}
