package com.kyeiiih.b2cpaymentservice.dto;

import com.kyeiiih.b2cpaymentservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionStatusResponse {
    private String transactionId;
    private PaymentStatus status;
    private String details;
    private String phoneNumber;
    private double amount;
    private String currency;
}
