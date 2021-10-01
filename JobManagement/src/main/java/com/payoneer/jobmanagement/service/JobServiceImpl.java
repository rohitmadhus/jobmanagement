package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.repository.JobFlowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobFlowRepository jobFlowRepository;

    @Override
    public List<JobFlow> findAllJobFlows() {
        return jobFlowRepository.findAll();
    }

    @Override
    public void createJobFlow(JobFlow jobFlow) {
        jobFlowRepository.insert(jobFlow);
    }

    @Override
    public Optional<List<JobFlow>> findAllScheduledJobs() {
       return jobFlowRepository.findAllByScheduledIsTrue();
    }
}
