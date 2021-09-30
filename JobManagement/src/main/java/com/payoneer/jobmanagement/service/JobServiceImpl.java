package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.models.Job;
import com.payoneer.jobmanagement.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }
}
