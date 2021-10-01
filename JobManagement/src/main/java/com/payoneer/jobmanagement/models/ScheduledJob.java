package com.payoneer.jobmanagement.models;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "scheduled-jobs")
public class ScheduledJob {
    @Id
    private String id;
    private String jobId;
    @CreatedDate
    private Date createdDate;
    private int interval;
}
