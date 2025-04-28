package com.kyeiiih.b2cpaymentservice.dto;

import lombok.Data;

@Data
public class SmsRequest {
    private String phoneNumber;
    private String message;
}
