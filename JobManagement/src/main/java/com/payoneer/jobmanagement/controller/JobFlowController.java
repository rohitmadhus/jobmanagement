package com.payoneer.jobmanagement.controller;

import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.service.JobService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/jobs")
public class JobFlowController {

    private final JobService jobService;
    private final Logger logger = LoggerFactory.getLogger(JobFlowController.class);

    @GetMapping
    public List<JobFlow> fetchAllJobsFlows() {
        return jobService.findAllJobFlows();
    }

    @PostMapping("/create")
    public void addScheduledJobFlow(@RequestBody JobFlow jobFlow) throws Exception {
        logger.info("Create job request received : ", jobFlow);
        if (jobFlow.getName() != null && jobFlow.getJobType() != null) {
            JobFlow createdJob = jobService.createJobFlow(jobFlow);
            logger.info("Job Flow Created", createdJob);
            jobService.runJob(createdJob);

        } else {
            logger.error("Job creation failed, Required data not found");
        }
    }

    /**
     * The scheduler to run the scheduled job
     * every 15sec of every min hr day
     */
    @Scheduled(cron = "0/15 * * * * *")
    public void runScheduledJob() {
//        Optional<List<JobFlow>> jobsFlows = jobService.findAllScheduledJobs();
//        if (jobsFlows.isPresent()) {
//            System.out.println(jobsFlows.get());
//        }
//        System.out.println("Running scheduled job");
    }
}
