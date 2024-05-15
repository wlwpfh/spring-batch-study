package com.example.springbatchstudy.scheduler;

import com.example.springbatchstudy.job.TestSchedulerJobConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final TestSchedulerJobConfiguration jobConfiguration;
    private final JobRepository jobRepository;

    @Scheduled(cron = "0/5 * * * * ?")
    public void runJob() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(jobConfiguration.testSchedulerJob(jobRepository), jobParameters);
    }
}
