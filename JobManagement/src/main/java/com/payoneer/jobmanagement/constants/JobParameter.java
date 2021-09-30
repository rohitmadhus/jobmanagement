package com.payoneer.jobmanagement.constants;

public class JobParameter{
    public enum Job_Type {
        DATA_EXTRACTION,
        DATA_CREATION,

    }

    public enum Job_Status{
        JOB_CREATED,
        JOB_RUNNING,
        JOB_FAILED,
        JOB_WAITING
    }
}


