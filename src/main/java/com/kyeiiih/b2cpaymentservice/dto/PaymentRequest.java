package com.kyeiiih.b2cpaymentservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String phoneNumber;
    private double amount;
    private String currency;
    private String transactionReference;
}
