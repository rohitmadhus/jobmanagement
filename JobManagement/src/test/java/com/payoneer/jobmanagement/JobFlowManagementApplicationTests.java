package com.payoneer.jobmanagement;

import com.payoneer.jobmanagement.config.JobConfig;
import com.payoneer.jobmanagement.controller.JobFlowController;
import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.service.JobService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


/**
 * Can configure test DB in application-test.properties in resource folder
 * For now due to infrastructure constraint writing directly to production DB
 */
@SpringBootTest(properties = "spring.profiles.active=test")
class JobFlowManagementApplicationTests {
    @Autowired
    JobConfig jobConfig;
    @Autowired
    JobFlowController jobFlowController;
    @Autowired
    JobService jobService;

    private static JobFlow jobFromDb1;
    private static JobFlow jobFromDb2;
    private static JobFlow jobFromDb3;
    private static JobFlow jobFromDb4;
    private static List<JobFlow> deleteJobFlowList = new ArrayList<>();


    /**
     * Create DB records to testcases
     */
    @BeforeEach
    void addDBData() {
        jobFromDb1 = jobService.createJobFlow(JobFlowManagementTestData.job1);
        jobFromDb2 = jobService.createJobFlow(JobFlowManagementTestData.job2);
        jobFromDb3 = jobService.createJobFlow(JobFlowManagementTestData.job3);
        jobFromDb4 = jobService.createJobFlow(JobFlowManagementTestData.job4);
    }

    @Test
    void contextLoads() {
    }

    /**
     * Executing job one after another
     */
    @Test
    void jobExecutionTest() throws Exception {
        JobFlow job1 = jobFlowController.createJobFlow(JobFlowManagementTestData.job1);
        deleteJobFlowList.add(job1);
        JobFlow job2 = jobFlowController.createJobFlow(JobFlowManagementTestData.job2);
        deleteJobFlowList.add(job2);
        JobFlow job3 = jobFlowController.createJobFlow(JobFlowManagementTestData.job3);
        deleteJobFlowList.add(job3);
        JobFlow job4 = jobFlowController.createJobFlow(JobFlowManagementTestData.job4);
        deleteJobFlowList.add(job4);
    }

    /**
     * Executing job one after another by queuing with priority
     * mimics the cron job also
     */
    @Test
    void jobExecutionByPriorityTest() throws Exception {
        JobConfig.pq.add(jobFromDb1);
        JobConfig.pq.add(jobFromDb2);
        JobConfig.pq.add(jobFromDb3);
        JobConfig.pq.add(jobFromDb4);
        jobService.runJob();
    }

    /**
     * Delete all mongo document created during testing
     */
    @AfterEach
    void deleteDataOfTestCase() {
        for (JobFlow jobFlow : deleteJobFlowList) {
            jobService.deleteJobFlow(jobFlow);
        }
        jobService.deleteJobFlow(jobFromDb1);
        jobService.deleteJobFlow(jobFromDb2);
        jobService.deleteJobFlow(jobFromDb3);
        jobService.deleteJobFlow(jobFromDb4);
    }

}
