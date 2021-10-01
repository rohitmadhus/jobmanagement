package com.payoneer.jobmanagement;

import com.payoneer.jobmanagement.constants.CreditCardUserParameter;
import com.payoneer.jobmanagement.constants.JobFlowParameter;
import com.payoneer.jobmanagement.models.CreditCardUser;
import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.repository.CreditCardUserRepository;
import com.payoneer.jobmanagement.repository.JobFlowRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

@EnableMongoAuditing
@EnableScheduling
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JobManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(JobFlowRepository repo){
		return args -> {

			JobFlow job = new JobFlow("job1", JobFlowParameter.Job_Status.JOB_CREATED, JobFlowParameter.Job_Type.DATA_CREATION,true);


			//repo.insert(list);
		};
	}

}
