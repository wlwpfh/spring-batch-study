package com.example.springbatchstudy.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExampleJob {

    @Scheduled(cron = "0/30 * * * * ?")
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Quartz Job 실행 중 " + new Date());
    }
}
