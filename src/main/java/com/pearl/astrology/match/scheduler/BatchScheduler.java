package com.pearl.astrology.match.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job matchScoringJob;

    // Run via cron expression configured in application.yml
    @Scheduled(cron = "${match.scoring.cron}")
    public void runMatchScoringJob() {
        log.info("Starting Match Scoring Job via Scheduler...");
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            
            jobLauncher.run(matchScoringJob, params);
            log.info("Match Scoring Job finished successfully.");
        } catch (Exception e) {
            log.error("Failed to run Match Scoring Job", e);
        }
    }
}
