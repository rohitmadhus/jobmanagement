package com.payoneer.jobmanagement.controller;

import com.payoneer.jobmanagement.config.JobConfig;
import com.payoneer.jobmanagement.constants.JobFlowParameter;
import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.service.JobService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public void createJobFlow(@RequestBody JobFlow jobFlow) throws Exception {
        logger.info("Create job request received : ", jobFlow);
        if (jobFlow.getName() != null && jobFlow.getJobType() != null && jobFlow.getJobPriority() != null) {
            JobFlow createdJob = jobService.createJobFlow(jobFlow);
            logger.info("Job Flow Created", createdJob);
            createdJob.setJobStatus(JobFlowParameter.Job_Status.JOB_QUEUEED);
            jobService.updateJobFlow(createdJob);
            if (!JobConfig.queueMode) {
                jobService.runJob();
            }
        } else {
            logger.error("Job flow creation failed, Required data not found");
        }
    }

    /**
     * The scheduler to run the scheduled job
     * every 15sec of every min hr day
     */
//    @Scheduled(cron = "0/15 * * * * *")
//    public void runScheduledJob() {
//        Optional<List<JobFlow>> jobsFlows = jobService.findAllScheduledJobs();
//        for (JobFlow job : jobsFlows.get()) {
//            job.setJobStatus(JobFlowParameter.Job_Status.JOB_QUEUEED);
//            jobService.updateJobFlow(job);
//        }
//        if (jobsFlows.isPresent()) {
//            JobConfig.pq.addAll(jobsFlows.get());
//            if (!JobConfig.queueMode) {
//                jobService.runJob();
//            }
//        }
//        System.out.println("Running scheduled job");
//    }
}
