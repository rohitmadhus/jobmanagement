package com.payoneer.jobmanagement.controller;

import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/jobs")
public class JobFlowController {

    private final JobService jobService;

    @GetMapping
    public List<JobFlow> fetchAllJobsFlows(){
        return jobService.findAllJobFlows();
    }

    @PostMapping("/schedule")
    public void addJobFlow(@RequestBody JobFlow jobFlow){
        jobService.createJobFlow(jobFlow);
    }

    /**
     * The scheduler to run the scheduled job
     * every 15sec of every min hr day
     */
    @Scheduled(cron = "0/15 * * * * *")
    public void runScheduledJob(){
      System.out.println("Running scheduled job");
    }
}
