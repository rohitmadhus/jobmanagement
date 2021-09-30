package com.payoneer.jobmanagement.service;

import com.payoneer.jobmanagement.models.Job;
import org.springframework.stereotype.Service;


import java.util.List;

public interface JobService {
    public List<Job> findAllJobs();
}
