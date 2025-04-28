package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.SmsRequest;
import com.kyeiiih.b2cpaymentservice.exceptions.SmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final SmsGateway smsGateway;

    public NotificationServiceImpl(SmsGateway smsGateway) {
        this.smsGateway = smsGateway;
    }

    @Override
    public void sendNotification(String phoneNumber, String message) {
        logger.info("Sending SMS to {}: {}", phoneNumber, message);
        try {
            SmsRequest smsRequest = new SmsRequest();
            smsRequest.setPhoneNumber(phoneNumber);
            smsRequest.setMessage(message);
            smsGateway.sendSms(smsRequest);
        } catch (SmsException e) {
            logger.error("Failed to send SMS to {}: {}", phoneNumber, e.getMessage());
        }
    }
}
