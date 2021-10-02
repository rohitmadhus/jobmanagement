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
public class JobFlow {
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

    public JobFlow(String name, boolean scheduled, JobFlowParameter.Job_Type jobType) {
        this.name = name;
        this.jobStatus = JobFlowParameter.Job_Status.JOB_CREATED;
        this.jobType = jobType;
        this.scheduled = scheduled;
    }
}
