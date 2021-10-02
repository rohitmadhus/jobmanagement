package com.payoneer.jobmanagement.models;

import com.payoneer.jobmanagement.constants.JobFlowParameter;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "job-flows")
public class JobFlow implements Runnable {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
    private String name;
    private boolean scheduled;
    private JobFlowParameter.Job_Status jobStatus;
    private JobFlowParameter.Job_Type jobType;
    private JobFlowParameter.Job_Priority jobPriority;

    public JobFlow(String name, boolean scheduled, JobFlowParameter.Job_Type jobType, JobFlowParameter.Job_Priority jobPriority) {
        this.name = name;
        this.jobStatus = JobFlowParameter.Job_Status.JOB_CREATED;
        this.jobType = jobType;
        this.scheduled = scheduled;
        this.jobPriority = jobPriority;
    }


    @Override
    public void run() {
    }
}
