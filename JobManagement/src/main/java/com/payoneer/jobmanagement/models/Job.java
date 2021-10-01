package com.payoneer.jobmanagement.models;

import com.payoneer.jobmanagement.constants.JobParameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "jobs")
public class Job {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
    private String name;
    private boolean isScheduled;
    private JobParameter.Job_Status jobStatus;
    private JobParameter.Job_Type jobType;

    public Job(String name, JobParameter.Job_Status jobStatus, JobParameter.Job_Type jobType){
        this.name = name;
        this.jobStatus = jobStatus;
        this.jobType = jobType;
    };


    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public JobParameter.Job_Status getJobStatus() {return jobStatus;}
    public void setJobStatus(JobParameter.Job_Status jobStatus) {this.jobStatus = jobStatus;}

    public JobParameter.Job_Type getJobType() {return jobType;}
    public void setJobType(JobParameter.Job_Type jobType) {this.jobType = jobType;}

    public Date getCreatedDate() {return createdDate;}
    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}

    public Date getLastModifiedDate() {return lastModifiedDate;}
    public void setLastModifiedDate(Date lastModifiedDate) {this.lastModifiedDate = lastModifiedDate;}

    public boolean isScheduled() {return isScheduled;}
    public void setScheduled(boolean scheduled) {isScheduled = scheduled;}

}
