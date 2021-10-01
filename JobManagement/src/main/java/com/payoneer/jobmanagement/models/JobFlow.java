package com.payoneer.jobmanagement.models;

import com.payoneer.jobmanagement.constants.JobFlowParameter;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "jobs")
public class JobFlow {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
    private String name;
    private boolean isScheduled;
    private Date nextRun;
    private JobFlowParameter.Job_Status jobStatus;
    private JobFlowParameter.Job_Type jobType;

    public JobFlow(String name, JobFlowParameter.Job_Status jobStatus, JobFlowParameter.Job_Type jobType,boolean isScheduled){
        this.name = name;
        this.jobStatus = jobStatus;
        this.jobType = jobType;
        this.isScheduled = isScheduled;
        nextRun = new Date();
    };

}
