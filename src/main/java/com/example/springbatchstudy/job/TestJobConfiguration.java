package com.example.springbatchstudy.job;

import com.example.springbatchstudy.id.UniqueRunIdIncrementer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TestJobConfiguration {
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job testJob(JobRepository jobRepository) {
        return new JobBuilder("testJob", jobRepository)
                .start(testStep(jobRepository, transactionManager))
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step testStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("testStep", jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("testStep  실행 중 !");
                return RepeatStatus.FINISHED;
            }
        }, platformTransactionManager).build();
    }
}
