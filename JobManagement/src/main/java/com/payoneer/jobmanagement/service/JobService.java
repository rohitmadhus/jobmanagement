package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.models.JobFlow;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface JobService {
    public List<JobFlow> findAllJobFlows();

    public JobFlow createJobFlow(JobFlow jobFlow);

    public Optional<List<JobFlow>> findAllScheduledJobs();

    public void runJob();

    public JobFlow updateJobFlow(JobFlow jobFlow);
}
