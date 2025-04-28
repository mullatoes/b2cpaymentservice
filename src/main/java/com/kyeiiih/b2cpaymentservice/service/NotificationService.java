package com.kyeiiih.b2cpaymentservice.service;

public interface NotificationService {
    void sendNotification(String recipientMobileNumber, String message);
}
