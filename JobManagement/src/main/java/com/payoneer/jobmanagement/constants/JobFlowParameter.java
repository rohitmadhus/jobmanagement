package com.payoneer.jobmanagement.constants;

public class JobFlowParameter {
    public enum Job_Type {
        DATA_EXTRACTION,
        DATA_CREATION,
        REPORT_GENERATION

    }

    public enum Job_Status {
        JOB_CREATED,
        JOB_RUNNING,
        JOB_FAILED,
        JOB_QUEUEED,
        JOB_COMPLETED
    }

    public enum Job_Priority {
        HIGH,
        MEDIUM,
        LOW
    }
}


