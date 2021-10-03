package com.payoneer.jobmanagement.constants;

public class JobFlowParameter {
    public enum Job_Type {
        MAIL_REPORT,
        MIGRATE_DATA,
        REPORT_GENERATION

    }

    public enum Job_Status {
        JOB_CREATED,
        JOB_RUNNING,
        JOB_FAILED,
        JOB_QUEUED,
        JOB_COMPLETED
    }

    public enum Job_Priority {
        HIGH,
        MEDIUM,
        LOW
    }
}


