package com.payoneer.jobmanagement.JobUtil;


import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.service.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityJobScheduler {

    @Autowired
    private JobServiceImpl jobService;

    private ExecutorService priorityJobPoolExecutor;
    private ExecutorService priorityJobScheduler
            = Executors.newSingleThreadExecutor();

    public static PriorityBlockingQueue<JobFlow> priorityQueue;

    public PriorityJobScheduler(Integer poolSize, Integer queueSize) {
        priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
        priorityQueue = new PriorityBlockingQueue(
                queueSize,
                Comparator.comparing(JobFlow::getJobPriority));
        priorityJobScheduler.execute(() -> {
            while (true) {
                try {
                    //System.out.println(priorityQueue.peek());
                    // jobService.runJobFromThread(priorityQueue.peek());
                    //jobService.runJobFromThread(priorityQueue.peek());
                } catch (Exception e) {
                    // exception needs special handling
                    break;
                }
            }
        });
    }

    public void scheduleJob(JobFlow job) {
        priorityQueue.add(job);
    }
}
