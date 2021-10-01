package com.payoneer.jobmanagement.models;

import com.payoneer.jobmanagement.constants.CreditCardUserParameter;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "credit-card-users")
public class CreditCardUser {
    @Id
    private String id;
    private String name;
    private String address;
    private String ccNumber;
    private float billAmount;
    private CreditCardUserParameter.BILL_STATUS status;
    private String email;

    public CreditCardUser(String name, String address, String ccNumber, float billAmount, CreditCardUserParameter.BILL_STATUS status, String email){
        this.name = name;
        this.address = address;
        this.ccNumber = ccNumber;
        this.billAmount = billAmount;
        this.status = status;
        this.email = email;
    }

}
