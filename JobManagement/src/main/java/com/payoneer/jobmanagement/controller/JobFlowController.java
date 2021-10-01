package com.payoneer.jobmanagement.controller;

import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public void addScheduledJobFlow(@RequestBody String name) throws Exception {
        if(name != null) {
            JobFlow jobFlow = new JobFlow(name, true);
            jobService.createJobFlow(jobFlow);
        }else{
            throw new Exception("Scheduled job creation failed. Job name not found");
        }
    }

    @PostMapping
    public void addJobFlow(@RequestBody String name) throws Exception {
        if(name != null) {
            JobFlow jobFlow = new JobFlow(name, false);
            jobService.createJobFlow(jobFlow);
        }else{
            throw new Exception("Job creation failed. Job name not found");
        }
    }

    /**
     * The scheduler to run the scheduled job
     * every 15sec of every min hr day
     */
    @Scheduled(cron = "0/15 * * * * *")
    public void runScheduledJob(){
        Optional<List<JobFlow>> jobs = jobService.findAllScheduledJobs();
         if(jobs.isPresent()){
             System.out.println(jobs.get());
           }
      System.out.println("Running scheduled job");
    }
}
