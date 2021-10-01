package com.payoneer.jobmanagement.controller;

import com.payoneer.jobmanagement.models.Job;
import com.payoneer.jobmanagement.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping
    public List<Job> fetchAllJobs(){
        return jobService.findAllJobs();
    }

    //@PostMapping()
}
