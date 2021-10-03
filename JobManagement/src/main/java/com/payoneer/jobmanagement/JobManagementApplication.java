package com.payoneer.jobmanagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableMongoAuditing
@EnableScheduling
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JobManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobManagementApplication.class, args);
	}

}
