package com.pearl.astrology.match.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatchSchedulerTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job matchScoringJob;

    @InjectMocks
    private BatchScheduler batchScheduler;

    @Test
    public void testRunMatchScoringJobSuccessfully() throws Exception {
        JobExecution jobExecution = mock(JobExecution.class);
        when(jobLauncher.run(eq(matchScoringJob), any(JobParameters.class))).thenReturn(jobExecution);

        batchScheduler.runMatchScoringJob();

        verify(jobLauncher, times(1)).run(eq(matchScoringJob), any(JobParameters.class));
    }

    @Test
    public void testRunMatchScoringJobHandlesException() throws Exception {
        when(jobLauncher.run(eq(matchScoringJob), any(JobParameters.class)))
                .thenThrow(new RuntimeException("Simulated launcher failure"));

        // Verify no exception is bubbled up
        batchScheduler.runMatchScoringJob();

        verify(jobLauncher, times(1)).run(eq(matchScoringJob), any(JobParameters.class));
    }
}
