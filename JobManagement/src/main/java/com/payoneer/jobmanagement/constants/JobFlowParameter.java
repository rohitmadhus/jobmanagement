package com.payoneer.jobmanagement.constants;

public class JobFlowParameter {
    public enum Job_Type {
        DATA_EXTRACTION,
        DATA_CREATION,
        REPORT_GENERATION

    }

    public enum Job_Status{
        JOB_CREATED,
        JOB_RUNNING,
        JOB_FAILED,
        JOB_WAITING,
        JOB_COMPLETED
    }
}


