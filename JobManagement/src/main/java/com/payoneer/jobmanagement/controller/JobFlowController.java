package com.payoneer.jobmanagement.controller;

import com.payoneer.jobmanagement.config.JobConfig;
import com.payoneer.jobmanagement.constants.JobFlowParameter;
import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.service.JobService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public JobFlow createJobFlow(@RequestBody JobFlow jobFlow) throws Exception {
        logger.info("Create job request received : " + jobFlow.toString());
        if (jobFlow.getName() != null && jobFlow.getJobType() != null && jobFlow.getJobPriority() != null) {
            JobFlow createdJob = jobService.createJobFlow(jobFlow);
            logger.info("Job Flow Created : " + createdJob.toString());
            createdJob.setJobStatus(JobFlowParameter.Job_Status.JOB_QUEUED);
            jobService.updateJobFlow(createdJob);
            JobConfig.pq.add(createdJob);
            if (!JobConfig.queueMode) {
                jobService.runJob();
            }
            return createdJob;
        } else {
            logger.error("Job flow creation failed, Required data not found");
        }
        return null;
    }

    /**
     * The scheduler to run the scheduled job
     * "," to specify diff Value, * matches every Value, - range of Value
     * "/" after specified time
     * sec min hr day month week
     * every 1 minute
     */
    @Scheduled(cron = "* */1 * * * *")
    public void runScheduledJob() {
        logger.info("Cron job started");
        Optional<List<JobFlow>> jobsFlows = jobService.findAllScheduledJobs();
        for (JobFlow job : jobsFlows.get()) {
            job.setJobStatus(JobFlowParameter.Job_Status.JOB_QUEUED);
            jobService.updateJobFlow(job);
        }

        if (jobsFlows.isPresent()) {
            JobConfig.pq.addAll(jobsFlows.get());
            logger.info("Jobs added to queue");
            if (!JobConfig.queueMode) {
                jobService.runJob();
            }
        }

    }
}
