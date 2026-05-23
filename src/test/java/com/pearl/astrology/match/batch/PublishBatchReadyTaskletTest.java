package com.pearl.astrology.match.batch;

import com.pearl.astrology.match.kafka.MatchProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublishBatchReadyTaskletTest {

    @Mock
    private MatchProducer matchProducer;

    @Mock
    private StepContribution stepContribution;

    @Mock
    private ChunkContext chunkContext;

    @Mock
    private StepContext stepContext;

    @InjectMocks
    private PublishBatchReadyTasklet tasklet;

    @Test
    public void testExecuteSuccessfully() {
        when(chunkContext.getStepContext()).thenReturn(stepContext);
        when(stepContext.getJobExecutionContext()).thenReturn(Collections.singletonMap("totalUsers", 42));

        RepeatStatus status = tasklet.execute(stepContribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, status);
        verify(matchProducer, times(1)).publishBatchReady(42);
    }
}
