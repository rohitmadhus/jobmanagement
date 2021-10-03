package com.payoneer.jobmanagement.repository;


import com.payoneer.jobmanagement.models.CreditCardUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditCardUserRepository extends MongoRepository<CreditCardUser,String> {
}
