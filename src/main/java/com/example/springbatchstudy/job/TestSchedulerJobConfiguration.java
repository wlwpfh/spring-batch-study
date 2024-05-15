package com.example.springbatchstudy.job;

import com.example.springbatchstudy.id.UniqueRunIdIncrementer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TestSchedulerJobConfiguration {
    private final PlatformTransactionManager transactionManager;

    @Bean
    @Primary
    public Job testSchedulerJob(JobRepository jobRepository) {
        return new JobBuilder("testSchedulerJob", jobRepository)
                .incrementer(new UniqueRunIdIncrementer())
                .start(testSchedulerStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step testSchedulerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("testSchedulerStep", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("testSchedulerStep  실행 중 !");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }
}
