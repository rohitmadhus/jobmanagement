package com.payoneer.jobmanagement.service;
import com.payoneer.jobmanagement.models.JobFlow;
import java.util.List;
import java.util.Optional;

public interface JobService {
    public List<JobFlow> findAllJobFlows();
    public JobFlow createJobFlow(JobFlow jobFlow);
    public Optional<List<JobFlow>> findAllScheduledJobs();
    public void runJob(JobFlow jobFlow);
    public JobFlow updateJobFlow(JobFlow jobFlow);
}
