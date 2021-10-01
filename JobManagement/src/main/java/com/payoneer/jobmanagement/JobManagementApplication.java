package com.payoneer.jobmanagement;

import com.payoneer.jobmanagement.constants.JobParameter;
import com.payoneer.jobmanagement.models.Job;
import com.payoneer.jobmanagement.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class JobManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobManagementApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(JobRepository repo){
//		return args -> {
//			Job job = new Job("job1",JobParameter.Job_Status.JOB_CREATED, JobParameter.Job_Type.DATA_CREATION);
//			repo.insert(job);
//		};
//	}

}
