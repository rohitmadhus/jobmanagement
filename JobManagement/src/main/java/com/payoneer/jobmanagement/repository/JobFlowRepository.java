package com.payoneer.jobmanagement.repository;

import com.payoneer.jobmanagement.models.JobFlow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JobFlowRepository extends MongoRepository<JobFlow,String> {
    Optional<JobFlow> findJobFlowByScheduledIsTrue();
}
