package com.payoneer.jobmanagement.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "credit-card-user")
public class CreditCardUser {
    @Id
    private String id;
    private String name;
    private String address;
    private String ccNumber;
    private float billAmount;
    private Date dueDate;
    private String status;
    private String email;
}
