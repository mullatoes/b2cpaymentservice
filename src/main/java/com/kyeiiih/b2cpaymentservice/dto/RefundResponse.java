package com.kyeiiih.b2cpaymentservice.dto;

import lombok.Data;

@Data
public class RefundResponse {
    private String refundId;
    private String transactionId;
    private String status;
    private String message;
}
