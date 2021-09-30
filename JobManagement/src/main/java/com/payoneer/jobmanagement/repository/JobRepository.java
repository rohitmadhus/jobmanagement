package com.payoneer.jobmanagement.repository;

import com.payoneer.jobmanagement.models.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job,String> {
}
