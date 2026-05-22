package com.pearl.astrology.match.batch;

import com.pearl.astrology.match.kafka.MatchProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * Tasklet that runs after the batch job completes to notify services that matches are ready.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PublishBatchReadyTasklet implements Tasklet {

    private final MatchProducer matchProducer;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("Batch scoring job completed. Triggering Kafka notification.");
        
        // In a real scenario, you might get the count from the job execution context
        int totalProcessed = (int) chunkContext.getStepContext().getJobExecutionContext().getOrDefault("totalUsers", 0);
        
        matchProducer.publishBatchReady(totalProcessed);
        
        return RepeatStatus.FINISHED;
    }
}
