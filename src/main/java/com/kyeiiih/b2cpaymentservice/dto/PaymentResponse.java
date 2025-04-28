package com.kyeiiih.b2cpaymentservice.dto;

import com.kyeiiih.b2cpaymentservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentResponse {
    private String transactionId;
    private PaymentStatus status;
    private String message;
}
