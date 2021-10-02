package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.config.JobConfig;
import com.payoneer.jobmanagement.constants.JobFlowParameter;
import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.repository.JobFlowRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobFlowRepository jobFlowRepository;
    private final JobConfig jobConfig;
    private final JobLauncher jobLauncher;
    private final Logger logger = LoggerFactory.getLogger(JobService.class);


    @Override
    public List<JobFlow> findAllJobFlows() {
        return jobFlowRepository.findAll();
    }

    @Override
    public JobFlow createJobFlow(JobFlow jobFlow) {
        return jobFlowRepository.save(jobFlow);
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
    public void runJob() {
        while (!JobConfig.pq.isEmpty()) {
            JobConfig.queueMode = true;
            logger.info("JobExecutor Started");
            JobFlow jobFlow = JobConfig.pq.poll();
            try {
                jobFlow.setJobStatus(JobFlowParameter.Job_Status.JOB_RUNNING);
                updateJobFlow(jobFlow);
                JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).addString("id", jobFlow.getId())
                        .toJobParameters();
                if (jobFlow.getJobType().equals(JobFlowParameter.Job_Type.REPORT_GENERATION)) {
                    jobLauncher.run(jobConfig.createReport(), jobParameters);
                } else {
                    throw new Exception("Job type not found");
                }
                logger.info("JobExecutor Completed");
                jobFlow.setJobStatus(JobFlowParameter.Job_Status.JOB_COMPLETED);
                updateJobFlow(jobFlow);

            } catch (Exception e) {
                logger.error("JobExecutor Completed", e);
                jobFlow.setJobStatus(JobFlowParameter.Job_Status.JOB_FAILED);
                updateJobFlow(jobFlow);
            }
        }
        JobConfig.queueMode = true;
    }
}
