package com.kyeiiih.b2cpaymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class B2CPaymentResponse {
    private String transactionId;
    private String status;
    private String message;
    private String gatewayTransactionId;
}
