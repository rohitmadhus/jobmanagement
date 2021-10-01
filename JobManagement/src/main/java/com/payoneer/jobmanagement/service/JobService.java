package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.models.JobFlow;


import java.util.List;
import java.util.Optional;

public interface JobService {
    public List<JobFlow> findAllJobFlows();
    public void createJobFlow(JobFlow jobFlow);
    public Optional<List<JobFlow>> findAllScheduledJobs();
}
