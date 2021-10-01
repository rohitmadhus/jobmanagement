package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.models.JobFlow;


import java.util.List;

public interface JobService {
    public List<JobFlow> findAllJobFlows();
    public void createJobFlow(JobFlow jobFlow);
}
