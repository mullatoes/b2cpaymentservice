package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.SmsRequest;
import com.kyeiiih.b2cpaymentservice.exceptions.SmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsGatewayImpl implements SmsGateway {

    private static final Logger logger = LoggerFactory.getLogger(SmsGatewayImpl.class);

    @Override
    public void sendSms(SmsRequest request) throws SmsException {
        //Actual SMS Provider integration e.g African Stalking, Speeder
        logger.info("Mock SMS sent to {}: {}", request.getPhoneNumber(), request.getMessage());
    }
}
