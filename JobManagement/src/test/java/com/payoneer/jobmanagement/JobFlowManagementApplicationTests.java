package com.payoneer.jobmanagement;

import com.payoneer.jobmanagement.config.JobConfig;
import com.payoneer.jobmanagement.constants.JobFlowParameter;
import com.payoneer.jobmanagement.controller.JobFlowController;
import com.payoneer.jobmanagement.models.JobFlow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobFlowManagementApplicationTests {
    @Autowired
    JobConfig jobConfig;
    @Autowired
    JobFlowController jobFlowController;

    private static Integer POOL_SIZE = 1;
    private static Integer QUEUE_SIZE = 10;
    private static JobFlow job1 = new JobFlow("Job1", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.LOW);
    private static JobFlow job2 = new JobFlow("Job2", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.HIGH);
    private static JobFlow job3 = new JobFlow("Job3", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.MEDIUM);
    private static JobFlow job4 = new JobFlow("Job4", true, JobFlowParameter.Job_Type.REPORT_GENERATION, JobFlowParameter.Job_Priority.HIGH);

    @Test
    void contextLoads() {
    }

    @Test
    void jobPriorityTest() throws Exception {

        jobFlowController.createJobFlow(job1);
        jobFlowController.createJobFlow(job2);
        jobFlowController.createJobFlow(job3);
        jobFlowController.createJobFlow(job4);

    }

}
