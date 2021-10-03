package com.payoneer.jobmanagement;

import com.payoneer.jobmanagement.constants.JobFlowParameter;
import com.payoneer.jobmanagement.models.JobFlow;

public class JobFlowManagementTestData {

    public static JobFlow job1 = new JobFlow("Test-Job1", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.LOW);
    public static JobFlow job2 = new JobFlow("Test-Job2", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.HIGH);
    public static JobFlow job3 = new JobFlow("Test-Job3", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.MEDIUM);
    public static JobFlow job4 = new JobFlow("Test-Job4", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.HIGH);
}
