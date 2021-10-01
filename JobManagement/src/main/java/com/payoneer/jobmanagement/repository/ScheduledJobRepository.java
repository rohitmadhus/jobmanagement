package com.payoneer.jobmanagement.repository;

import com.payoneer.jobmanagement.models.ScheduledJob;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduledJobRepository extends MongoRepository<ScheduledJob,String> {
}
