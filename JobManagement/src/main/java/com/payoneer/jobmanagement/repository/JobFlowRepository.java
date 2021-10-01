package com.payoneer.jobmanagement.repository;

import com.payoneer.jobmanagement.models.JobFlow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JobFlowRepository extends MongoRepository<JobFlow,String> {
    Optional<List<JobFlow>> findAllByScheduledIsTrue();
}
