package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.config.JobConfig;
import com.payoneer.jobmanagement.constants.JobFlowParameter;
import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.repository.JobFlowRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobFlowRepository jobFlowRepository;
    private final JobConfig jobConfig;
    @Qualifier(value = "runReportCreationJob")
    private final Job runReportCreationJob;
    private final JobLauncher jobLauncher;
    private final Logger logger = LoggerFactory.getLogger(JobService.class);

    @Override
    public List<JobFlow> findAllJobFlows() {
        return jobFlowRepository.findAll();
    }

    @Override
    public JobFlow createJobFlow(JobFlow jobFlow) {
        return jobFlowRepository.insert(jobFlow);
    }

    @Override
    public Optional<List<JobFlow>> findAllScheduledJobs() {
        return jobFlowRepository.findAllByScheduledIsTrue();
    }

    @Override
    public JobFlow updateJobFlow(JobFlow jobFlow) {
        return jobFlowRepository.save(jobFlow);
    }

    @Override
    public void runJob(JobFlow jobflow) {
        try {
            logger.info("Job Started");
            jobflow.setJobStatus(JobFlowParameter.Job_Status.JOB_RUNNING);
            updateJobFlow(jobflow);
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            //jobLauncher.run(runReportCreationJob, jobParameters);
            jobLauncher.run(jobConfig.runReportCreationJob(), jobParameters);
            logger.info("Job Completed");
            jobflow.setJobStatus(JobFlowParameter.Job_Status.JOB_COMPLETED);
            updateJobFlow(jobflow);
        } catch (Exception e) {
            logger.error("Job Completed", e);
            jobflow.setJobStatus(JobFlowParameter.Job_Status.JOB_FAILED);
            updateJobFlow(jobflow);
        }
    }
}
