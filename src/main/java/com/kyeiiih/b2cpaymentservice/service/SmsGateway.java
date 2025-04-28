package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.SmsRequest;
import com.kyeiiih.b2cpaymentservice.exceptions.SmsException;

public interface SmsGateway {
    void sendSms(SmsRequest request) throws SmsException;
}
